package com.example.transportation4oku.oku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.transportation4oku.R
import com.google.firebase.firestore.FirebaseFirestore

class OKUBookingDetail : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okubooking_detail)
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

        db = FirebaseFirestore.getInstance()
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
    }
}