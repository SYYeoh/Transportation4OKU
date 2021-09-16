package com.example.transportation4oku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class AdminBookingDetail : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_booking_detail)
        auth = FirebaseAuth.getInstance()

        var dateTV = findViewById<TextView>(R.id.dateAD)
        val nameTV = findViewById<TextView>(R.id.nameAD)
        val statusTV = findViewById<TextView>(R.id.statusAD)
        val pickTV = findViewById<TextView>(R.id.pickAD)
        val destTV = findViewById<TextView>(R.id.destAD)

        val intent = intent
        val date: String = intent?.getStringExtra("date").toString()
        val name: String = intent?.getStringExtra("name").toString()
        val from: String = intent?.getStringExtra("from").toString()
        val to: String = intent?.getStringExtra("to").toString()
        val status: String = intent?.getStringExtra("status").toString()

        //dateTV = date.text

    }
}