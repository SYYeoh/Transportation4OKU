package com.example.transportation4oku.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.transportation4oku.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdminMain : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        val fStore = FirebaseFirestore.getInstance()

        val booking = findViewById<Button>(R.id.AdminBookingbtn)
        booking.setOnClickListener {
            val intent = Intent(this, AdminManageBooking::class.java)
            startActivity(intent)
        }

        val user = findViewById<Button>(R.id.AdminUserBtn)
        user.setOnClickListener {
            val intent = Intent(this, AdminManageOKU::class.java)
            startActivity(intent)
        }

        val cg = findViewById<Button>(R.id.AdminCGAccBtn)
        cg.setOnClickListener {
            val intent = Intent(this, AdminManageCG::class.java)
            startActivity(intent)
        }

    }
}