package com.example.transportation4oku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.transportation4oku.admin.AdminLogin
import com.example.transportation4oku.caregiver.CaregiverLogin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val okubtn = findViewById<Button>(R.id.OKUbtn)
        okubtn.setOnClickListener {
            val intent = Intent(this, OKULogin::class.java)
            startActivity(intent)
            finish()
        }

        val cgbtn = findViewById<Button>(R.id.Caregiverbtn)
        cgbtn.setOnClickListener {
            val intent = Intent(this, CaregiverLogin::class.java)
            startActivity(intent)
            finish()
        }

        val adminbtn = findViewById<Button>(R.id.Adminbtn)
        adminbtn.setOnClickListener {
            val intent = Intent(this, AdminLogin::class.java)
            startActivity(intent)
            finish()
        }
    }
}