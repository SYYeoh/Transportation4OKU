package com.example.transportation4oku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CGMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cgmain)

        //button
        val pendBtn = findViewById<Button>(R.id.PendingBookingBtn)
        val acceptedBtn = findViewById<Button>(R.id.cgAcceptedBtn)
        val tipsBtn = findViewById<Button>(R.id.tipsBtn)
        val manageDetail = findViewById<Button>(R.id.cgUserDetailbtn)

        pendBtn.setOnClickListener {
            val intent = Intent(this, CGPendingBooking::class.java)
            startActivity(intent)
            finish()
        }

        acceptedBtn.setOnClickListener {
            /*val intent = Intent(this, OKULogin::class.java)
            startActivity(intent)
            finish()*/
        }

        tipsBtn.setOnClickListener {
            /*val intent = Intent(this, OKULogin::class.java)
            startActivity(intent)
            finish()*/
        }

        manageDetail.setOnClickListener {
            /*val intent = Intent(this, OKULogin::class.java)
            startActivity(intent)
            finish()*/
        }
    }
}