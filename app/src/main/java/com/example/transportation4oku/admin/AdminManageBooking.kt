package com.example.transportation4oku.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.transportation4oku.R
import com.google.firebase.firestore.*

class AdminManageBooking : AppCompatActivity() {
    private lateinit var  recyclerView: RecyclerView
    private lateinit var adArrayList : ArrayList<AdminBooking>
    private lateinit var adAdapter : AdminBookingAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_manage_booking)

        val toMain = findViewById<Button>(R.id.toAdminMainbtn)
        toMain.setOnClickListener {
            val intent = Intent(this, AdminMain::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.AdminBookingList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        adArrayList = arrayListOf()

        adAdapter = AdminBookingAdapter(adArrayList)

        recyclerView.adapter = adAdapter
        adAdapter.setOnItemClickListener(object : AdminBookingAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@AdminManageBooking, AdminBookingDetail::class.java)
                intent.putExtra("id", adArrayList[position].id)
                startActivity(intent)
            }
        })
        EventChangeListener()

    }
    private fun EventChangeListener(){
        db = FirebaseFirestore.getInstance()
        db.collection("Booking Detail").
        addSnapshotListener(object:EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    Log.e("Firestore Error",error.message.toString())
                    return
                }

                for (dc : DocumentChange in value?.documentChanges!!){

                    if (dc.type == DocumentChange.Type.ADDED){

                        adArrayList.add(dc.document.toObject(AdminBooking::class.java))
                    }
                }
                adAdapter.notifyDataSetChanged()
            }
        })
    }

    /*private fun setUpRecyclerview() {
        val query : Query = docRef.orderBy("status", Query.Direction.ASCENDING)
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<AdminBooking> = FirestoreRecyclerOptions
            .Builder<AdminBooking>().setQuery(query, AdminBooking::class.java).build();
        val db = Firebase.firestore

        //swipe to delete
        *//*ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adAdapter!!.deleteBooking(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(recyclerView)*//*

        *//*var adapter = AdminBookingAdapter(adArrayList)*//*
        adAdapter = AdminBookingAdapter(firestoreRecyclerOptions);

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adAdapter
        *//*AdminBookingAdapter.onItemClickListener{

        }*//*

    }*/


    /*private fun EventChangeListener() {
        fStore.collection("Booking Detail")
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("TAG", error.message.toString())
                        return
                    }
                    for (dc : DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            adArrayList.add(dc.document.toObject(AdminBooking::class.java))
                        }
                    }
                    adAdapter.notifyDataSetChanged()
                }
            })
    }*/
}