package com.example.transportation4oku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

class LocationDetail : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_detail)
        val bundle : Bundle? = intent.extras
        val locationID = bundle!!.getString("ID")

        var locationName: String? = null
        var locationAddress: String? = null
        var locationDescription: String? = null
        var locationCoordinate: GeoPoint? = null

        val txtDes = findViewById<TextView>(R.id.txtDes)
        val txtName = findViewById<TextView>(R.id.txtLocationName)
        val lblAddress = findViewById<TextView>(R.id.lblAddress)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnReview = findViewById<Button>(R.id.btnReview)
        val btnBook = findViewById<Button>(R.id.btnBook)

        fun launchMap(locationLatLng: LatLng, locationName: String){
            mapFragment = supportFragmentManager.findFragmentById(R.id.map1) as SupportMapFragment
            mapFragment.getMapAsync {
                googleMap = it
                googleMap.clear()
                googleMap.addMarker(MarkerOptions().position(locationLatLng).title(locationName))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 17f))
            }
        }

        db = FirebaseFirestore.getInstance()
        db.collection("Location").document(locationID.toString()).
        addSnapshotListener(this) {
                value, e ->
            locationName = value!!.getString("Name").toString()
            locationAddress = value!!.getString("Address").toString()
            locationDescription = value!!.getString("Description").toString()
            locationCoordinate = value!!.getGeoPoint("Coordinate")

            txtName.text = locationName
            txtDes.text = locationDescription
            lblAddress.text = locationAddress
            launchMap(LatLng(locationCoordinate!!.latitude, locationCoordinate!!.longitude), locationName!!)
        }
        btnBook.setOnClickListener {
            val intent = Intent(this,TransportBooking::class.java)
            intent.putExtra("ID",locationID)
            startActivity(intent)
        }
        btnReview.setOnClickListener {
            Toast.makeText(this, "GOTO REVIEW PAGE", Toast.LENGTH_SHORT).show()
        }
        btnBack.setOnClickListener {
            val intent = Intent(this,FindAPlace::class.java)
            startActivity(intent)
        }
    }
}