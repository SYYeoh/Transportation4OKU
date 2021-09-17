package com.example.transportation4oku.caregiver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.transportation4oku.R
import com.google.firebase.firestore.*

class CGBookingPending : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookingDetailArrayList: ArrayList<PendingDetail>
    private lateinit var myAdapter: CGBookingPendingAdapter
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cgbooking_pending)
        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        bookingDetailArrayList = arrayListOf()
        myAdapter = CGBookingPendingAdapter(bookingDetailArrayList)
        recyclerView.adapter = myAdapter
        myAdapter.setOnItemClickListener(object: CGBookingPendingAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                //Toast.makeText(this@MainActivity,"You Clicked on item no. $position",Toast.LENGTH_LONG).show()
                val intent = Intent(this@CGBookingPending, CGBookingDetail::class.java)
                intent.putExtra("id",bookingDetailArrayList[position].id)
                startActivity(intent)
                finish()
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

                        bookingDetailArrayList.add(dc.document.toObject(PendingDetail::class.java))
                    }
                }
                myAdapter.notifyDataSetChanged()
            }
        })
    }
}