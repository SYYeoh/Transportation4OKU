package com.example.transportation4oku.caregiver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.transportation4oku.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class CGBookingDetail : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cgbooking_detail)
        val db = FirebaseFirestore.getInstance()

        val bundle : Bundle?= intent.extras
        val Bookid = bundle!!.getLong("id").toString()

        val tvDate : TextView = findViewById(R.id.tvDisplayDate)
        val tvTime : TextView = findViewById(R.id.tvDisplayTime)
        val tvID : TextView = findViewById(R.id.tvDisplayID)
        val tvFrom : TextView = findViewById(R.id.tvPickUpLocation)
        val tvTo : TextView = findViewById(R.id.tvDestination)
        var date:String? = null
        var time:String? = null
        var from:String? = null
        var to:String? = null
        var name:String? = null

        //button
        val back = findViewById<Button>(R.id.backcgbtBtn)
        val accept = findViewById<Button>(R.id.acceptcgbdBtn)

        val docRef = db.collection("Booking Detail").document(Bookid.toString())
        docRef.addSnapshotListener(this){
                value,error ->
            date = value?.getString("date")
            time = value?.getString("time")
            from = value?.getString("from")
            to = value?.getString("to")

            Log.d("Data","date $date")
            Log.d("Data","time $time")

            tvDate.text = date
            tvTime.text = time
            tvID.text = Bookid
            tvFrom.text = from
            tvTo.text = to

        }

        back.setOnClickListener {
            val intent = Intent(this, CGBookingPending::class.java)
            startActivity(intent)
            finish()
        }

        accept.setOnClickListener {
            val user = Firebase.auth.currentUser
            user?.let {
                name = user.displayName
            }
            val acceptRef = db.collection("Booking Detail").document(Bookid.toString())
            acceptRef.update("status", "accepted", "caregiver", name).addOnSuccessListener {
                Toast.makeText(this, "This booking is Accepted!", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, CGBookingPending::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                    e -> Log.d("TAG", "Error accept", e)
                Toast.makeText(this, "Error occurred!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

}