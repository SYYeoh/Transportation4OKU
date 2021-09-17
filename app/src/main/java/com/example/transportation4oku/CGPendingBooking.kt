package com.example.transportation4oku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

class CGPendingBooking : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookingDetailArrayList: ArrayList<BookingDetail>
    private lateinit var myAdapter: CGPendingBookingAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cgpending_booking)

        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        bookingDetailArrayList = arrayListOf()

        myAdapter = CGPendingBookingAdapter(bookingDetailArrayList)
        myAdapter.setOnItemClickListener(object: CGPendingBookingAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                val intent = Intent(this@CGPendingBooking, CGBookingDetail::class.java)
                intent.putExtra("id",bookingDetailArrayList[position].id)
                startActivity(intent)
            }

        })
        EventChangeListener()
    }

    private fun EventChangeListener(){
        db = FirebaseFirestore.getInstance()
        db.collection("Booking Detail").whereEqualTo("status","pending").
        addSnapshotListener(object: EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    Log.e("Firestore Error",error.message.toString())
                    return
                }
                for (dc : DocumentChange in value?.documentChanges!!){

                    if (dc.type == DocumentChange.Type.ADDED){

                        bookingDetailArrayList.add(dc.document.toObject(BookingDetail::class.java))
                    }
                }
                myAdapter.notifyDataSetChanged()
            }
        })
    }
}