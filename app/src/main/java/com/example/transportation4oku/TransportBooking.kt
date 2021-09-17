package com.example.transportation4oku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.ktx.Firebase

class TransportBooking : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport_booking)
        val bundle : Bundle? = intent.extras
        val locationID = bundle!!.getString("ID")

        var locationName: String? = null
        var locaitonAddress: String? = null
        var locationDescription: String? = null
        var locationCoordinate: GeoPoint? = null
        var email: String? = null
        var name: String? = null

        val txtDes = findViewById<TextView>(R.id.txtDes)
        val txtLocationName = findViewById<TextView>(R.id.txtLocationName)
        val txtDateDD = findViewById<EditText>(R.id.txtDateDD)
        val txtDateMM = findViewById<EditText>(R.id.txtDateMM)
        val txtDateYYYY = findViewById<EditText>(R.id.txtDateYYYY)
        val txtTimeHH = findViewById<EditText>(R.id.txtTimeHH)
        val txtTimeMM = findViewById<EditText>(R.id.txtTimeMM)
        val rdAM = findViewById<RadioButton>(R.id.rdAM)
        val rdPM = findViewById<RadioButton>(R.id.rdPM)
        val txtPU = findViewById<EditText>(R.id.txtPU)
        val lblFieldName = findViewById<TextView>(R.id.lblFieldName)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnBook = findViewById<Button>(R.id.btnBook2)

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
            locaitonAddress = value!!.getString("Address").toString()
            locationDescription = value!!.getString("Description").toString()
            locationCoordinate = value!!.getGeoPoint("Coordinate")

            txtLocationName.text = locationName
            txtDes.text = locationDescription
            lblFieldName.text = locationName
            launchMap(LatLng(locationCoordinate!!.latitude, locationCoordinate!!.longitude), locationName!!)
        }
        btnBook.setOnClickListener {
            var valid = true
            if(txtDateDD.text.toString().toInt() > 31 || txtDateDD.text.toString().toInt() < 1 || txtDateDD.text.isEmpty()){
                valid = false
            }
            if(txtDateMM.text.toString().toInt() > 12 || txtDateMM.text.toString().toInt() < 1 || txtDateMM.text.isEmpty()){
                valid = false
            }
            if(txtDateYYYY.text.toString().toInt() < 2021 || txtDateYYYY.text.isEmpty()){
                valid = false
            }
            if(txtTimeHH.text.toString().toInt() > 12 || txtTimeHH.text.toString().toInt() < 1 || txtTimeHH.text.isEmpty()){
                valid = false
            }
            if(txtTimeMM.text.toString().toInt() > 59 || txtTimeMM.text.toString().toInt() < 1 || txtTimeMM.text.isEmpty()){
                valid = false
            }

            if(valid){
                var locationDate: String?= null
                var locationTime: String?= null
                val bookingID = System.currentTimeMillis()

                locationDate = txtDateDD.text.toString() + "/"
                locationDate += txtDateMM.text.toString() + "/"
                locationDate += txtDateYYYY.text.toString()
                locationTime = txtTimeHH.text.toString() + "."
                if(txtTimeMM.text.toString().length < 2){
                    locationTime += "0"
                }
                locationTime += txtTimeMM.text.toString()
                if(rdAM.isChecked){
                    locationTime += "AM"
                }
                else if(rdPM.isChecked){
                    locationTime += "PM"
                }
                val user = Firebase.auth.currentUser
                user?.let {
                    email = user.email
                }
                val nameRef = db.collection("OKU").document(email.toString())
                nameRef.addSnapshotListener(this){
                    value, error ->
                    name = value?.getString("name")
                }

                val locationHash = hashMapOf(
                    "caregiver" to "",
                    "date" to locationDate,
                    "from" to txtPU.text.toString(),
                    "id" to bookingID,
                    "oku" to name.toString(),
                    "status" to "pending",
                    "time" to locationTime,
                    "to" to txtLocationName.text.toString(),
                )
                val docRef = db.collection("Booking Detail").document(bookingID.toString())
                docRef.set(locationHash)

                //Toast.makeText(this, "GOTO MENU", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, OKUMain::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Incorrect Input!", Toast.LENGTH_SHORT).show()
            }
        }
        btnBack.setOnClickListener {
            val intent = Intent(this,LocationDetail::class.java)
            intent.putExtra("ID",locationID)
            startActivity(intent)
        }
    }

}