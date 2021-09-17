package com.example.transportation4oku.oku

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.transportation4oku.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.*

class FindAPlace : AppCompatActivity() {
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var recyclerView: RecyclerView
    private lateinit var locationClassArrayList: ArrayList<LocationClass>
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_aplace)
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
        fun launchMap(theLocation: LatLng, titleName: String) {
            mapFragment = supportFragmentManager.findFragmentById(R.id.map1) as SupportMapFragment
            mapFragment.getMapAsync {
                googleMap = it
                googleMap.isMyLocationEnabled = true
                googleMap.clear()
                googleMap.addMarker(MarkerOptions().position(theLocation).title(titleName))
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(theLocation, 17f))
            }
        }

        val txtDis = findViewById<TextView>(R.id.txtDistance)
        val btnNext = findViewById<TextView>(R.id.btnNext)
        val btnReturn = findViewById<TextView>(R.id.btnReturn)
        var passValue = ""

        fun getLocation(locationName: String, locationLat: Double, locationLng: Double){
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    val distanceGap = FloatArray(1)
                    Location.distanceBetween(
                        location!!.latitude, location!!.longitude,
                        locationLat, locationLng,
                        distanceGap
                    )
                    txtDis.text = "Distance = %.2f km".format(distanceGap[0] / 1000)
                }
            launchMap(LatLng(locationLat, locationLng), locationName)
        }

        fun changeButtons(locationType: String){
            recyclerView = findViewById(R.id.recycleView)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)

            locationClassArrayList = arrayListOf()

            locationAdapter = LocationAdapter(locationClassArrayList)

            recyclerView.adapter = locationAdapter
            locationAdapter.setOnItemClickListener(object: LocationAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    passValue = locationClassArrayList[position].ID.toString()
                    getLocation(
                        locationClassArrayList[position].Name.toString(),
                        locationClassArrayList[position].Coordinate!!.latitude,
                        locationClassArrayList[position].Coordinate!!.longitude
                    )
                }
            })
            EventChangeListener(locationType)
        }

        val xspinner = findViewById<Spinner>(R.id.spinner1)
        val dataListx = arrayOf("Hospital", "School", "Restaurant")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataListx)

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        xspinner.adapter = arrayAdapter
        xspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                changeButtons(dataListx[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not Yet Implemented")
            }
        }
        btnNext.setOnClickListener {
            if(passValue != ""){
                val intent = Intent(this, LocationDetail::class.java)
                intent.putExtra("ID",passValue)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Please Select a Location.", Toast.LENGTH_SHORT).show()
            }
        }
        btnReturn.setOnClickListener {
            val intent = Intent(this, OKUMain::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun EventChangeListener(locationType: String){
        db = FirebaseFirestore.getInstance()
        db.collection("Location").whereEqualTo("Type","$locationType").
        addSnapshotListener(object: EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    Log.e("Firestore Error",error.message.toString())
                    return
                }
                for (dc : DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED){
                        locationClassArrayList.add(dc.document.toObject(LocationClass::class.java))
                    }
                }
                locationAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            requestCode -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    googleMap.isMyLocationEnabled = false
                } else {
                    googleMap.isMyLocationEnabled = true
                }
            }
        }
    }
}