package com.example.transportation4oku.caregiver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.transportation4oku.MainActivity
import com.example.transportation4oku.R
import com.google.firebase.auth.FirebaseAuth

class CGMain : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cgmain)
        auth = FirebaseAuth.getInstance()

        //button
        val pendBtn = findViewById<Button>(R.id.PendingBookingBtn)
        val acceptedBtn = findViewById<Button>(R.id.cgAcceptedBtn)
        //val tipsBtn = findViewById<Button>(R.id.tipsBtn)
        val manageDetail = findViewById<Button>(R.id.cgUserDetailbtn)

        /*//pass from cglogin
        val bundle : Bundle?= intent.extras
        val email = bundle!!.getString("email")*/

        pendBtn.setOnClickListener {
            val intent = Intent(this, CGBookingPending::class.java)
            startActivity(intent)
        }

        acceptedBtn.setOnClickListener {
            val intent = Intent(this, CGBookingAccepted::class.java)
            startActivity(intent)
        }

        /*tipsBtn.setOnClickListener {
            *//*val intent = Intent(this, OKULogin::class.java)
            startActivity(intent)*//*
        }*/

        manageDetail.setOnClickListener {
            val intent = Intent(this, CGPersonalDetail::class.java)
            //intent.putExtra("email",email)
            startActivity(intent)
        }
    }
    /*public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null ) {
            val intent = Intent(this, CGMain::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }*/
}