package com.example.transportation4oku.caregiver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.transportation4oku.R
import com.google.firebase.firestore.*

class CGBookingAccepted : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var acceptedDetailArrayList: ArrayList<BookingDetail>
    private lateinit var myAdapter: CGBookingAcceptedAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cgbooking_accepted)

        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        acceptedDetailArrayList = arrayListOf()
        myAdapter = CGBookingAcceptedAdapter(acceptedDetailArrayList)
        recyclerView.adapter = myAdapter
        myAdapter.setOnItemClickListener(object: CGBookingAcceptedAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                //Toast.makeText(this@MainActivity,"You Clicked on item no. $position",Toast.LENGTH_LONG).show()
                val intent = Intent(this@CGBookingAccepted, CGAcceptedBookingDetail::class.java)
                intent.putExtra("id",acceptedDetailArrayList[position].id)
                startActivity(intent)
            }

        })
        EventChangeListener()
    }

    private fun EventChangeListener(){
        db = FirebaseFirestore.getInstance()
        db.collection("Booking Detail").whereEqualTo("status","accepted").
        addSnapshotListener(object: EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    Log.e("Firestore Error",error.message.toString())
                    return
                }
                for (dc : DocumentChange in value?.documentChanges!!){

                    if (dc.type == DocumentChange.Type.ADDED){

                        acceptedDetailArrayList.add(dc.document.toObject(BookingDetail::class.java))
                    }
                }
                myAdapter.notifyDataSetChanged()
            }
        })
    }
}