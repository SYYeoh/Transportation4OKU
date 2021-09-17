package com.example.transportation4oku.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.transportation4oku.MainActivity
import com.example.transportation4oku.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class AdminMain : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)
        auth = FirebaseAuth.getInstance()
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

        val lo = findViewById<Button>(R.id.adminLogOutBtn)
        lo.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    /*public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null ) {
            val intent = Intent(this, AdminMain::class.java)
            startActivity(intent)
            finish()
        }else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }*/
}