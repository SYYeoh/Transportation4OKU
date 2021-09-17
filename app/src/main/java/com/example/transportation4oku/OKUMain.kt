package com.example.transportation4oku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class OKUMain : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okumain)
        auth = FirebaseAuth.getInstance()

        //button
        val findAPlace = findViewById<Button>(R.id.FindAPlaceBtn)
        //val book = findViewById<Button>(R.id.BookServiceBtn)
        val viewBooking = findViewById<Button>(R.id.ViewBookingBtn)
        val manageAcc = findViewById<Button>(R.id.ManageAccBtn)

        findAPlace.setOnClickListener {
            val intent = Intent(this, FindAPlace::class.java)
            startActivity(intent)
        }

        /*book.setOnClickListener {
            val intent = Intent(this, TransportBooking::class.java)
            startActivity(intent)
        }*/

        viewBooking.setOnClickListener {
            val intent = Intent(this, OKUviewBooking::class.java)
            startActivity(intent)
        }

        manageAcc.setOnClickListener {
            val intent = Intent(this, OKUPersonalDetail::class.java)
            startActivity(intent)
        }
    }
    /*public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null ) {
            val intent = Intent(this, OKUMain::class.java)
            startActivity(intent)
            finish()
        }else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }*/
}