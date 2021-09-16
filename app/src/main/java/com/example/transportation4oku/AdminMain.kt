package com.example.transportation4oku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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
            finish()
        }

        val user = findViewById<Button>(R.id.AdminUserBtn)
        user.setOnClickListener {
            val intent = Intent(this, AdminManageUser::class.java)
            startActivity(intent)
            finish()
        }

    }
}