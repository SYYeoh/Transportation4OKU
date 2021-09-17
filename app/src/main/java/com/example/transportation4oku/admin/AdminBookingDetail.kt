package com.example.transportation4oku.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.transportation4oku.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdminBookingDetail : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    var date: String? = null
    var cg: String? = null
    var oku: String? = null
    var status: String? = null
    var pick: String? = null
    var dest: String? = null
    var time: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_booking_detail)
        val db = FirebaseFirestore.getInstance()

        //button
        val toAdminBooking = findViewById<Button>(R.id.toAMBbtn)
        val deleteBooking = findViewById<Button>(R.id.DeleteAD)

        //textview
        val dateTV = findViewById<TextView>(R.id.dateAD)
        val CGnameTV = findViewById<TextView>(R.id.cgNameAD)
        val OKUnameTV = findViewById<TextView>(R.id.nameAD)
        val statusTV = findViewById<TextView>(R.id.statusAD)
        val pickTV = findViewById<TextView>(R.id.pickAD)
        val destTV = findViewById<TextView>(R.id.destAD)
        val timeTV = findViewById<TextView>(R.id.timeAD)

        //get value passed from Admin Manage Booking
        val bundle : Bundle?= intent.extras
        val id = bundle!!.getLong("id" , -1)

        val bookIDTV = findViewById<TextView>(R.id.bookIDAD)
        Log.d("TAG", "id $id")

        bookIDTV.text = id.toString()

        toAdminBooking.setOnClickListener {
            val intent = Intent(this, AdminManageBooking::class.java)
            startActivity(intent)
            finish()
        }

        deleteBooking.setOnClickListener {
            db.collection("Booking Detail").document(id.toString()).delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Booking ID $id has been deleted Successfully!",
                    Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, AdminManageBooking::class.java))
                    finish()
                }.addOnFailureListener { e ->
                    Log.w("TAG", "Error deleting document", e)
                    Toast.makeText(this, "Error deleting booking", Toast.LENGTH_SHORT).show()
                }
        }

        val docRef = db.collection("Booking Detail").document(id.toString())
        docRef.addSnapshotListener(this) {
                value, error ->
            date = value?.getString("date")
            cg = value?.getString("caregiver")
            oku = value?.getString("oku")
            status = value?.getString("status")
            pick = value?.getString("from")
            dest = value?.getString("to")
            time = value?.getString("time")

            dateTV.text = date
            CGnameTV.text = cg
            statusTV.text = status
            pickTV.text = pick
            destTV.text = dest
            timeTV.text = time
            OKUnameTV.text = oku

        }
        /*docRef.addSnapshotListener(this) {
            document, error ->
            val date = document?.getString("date")
            dateTV.text = date
            OKUnameTV.text = document?.getString("name")
            statusTV.text = document?.getString("status")
            pickTV.text = document?.getString("from")
            destTV.text = document?.getString("to")
            Log.d("TAG","date $date")
        }*/
    }
}