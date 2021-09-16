package com.example.transportation4oku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.*

class AdminManageBooking : AppCompatActivity() {
    private val fStore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val docRef: CollectionReference = fStore.collection("Booking Detail");

    private var adAdapter : AdminBookingAdapter? = null
    private lateinit var  recyclerView: RecyclerView
    /*private lateinit var  recyclerView: RecyclerView
    private lateinit var adArrayList : ArrayList<AdminBooking>
    private lateinit var adAdapter : AdminBookingAdapter*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_manage_booking)

        recyclerView = findViewById(R.id.AdminBookingList)

        setUpRecyclerview()

        val toMain = findViewById<Button>(R.id.toAdminMainbtn)
        toMain.setOnClickListener {
            val intent = Intent(this, AdminMain::class.java)
            startActivity(intent)
        }

        /*recyclerView = findViewById(R.id.AdminBookingList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        adArrayList = arrayListOf()

        adAdapter = AdminBookingAdapter(adArrayList)

        recyclerView.adapter = adAdapter

        EventChangeListener()*/

    }

    private fun setUpRecyclerview() {
        val query : Query = docRef.orderBy("status", Query.Direction.ASCENDING)
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<AdminBooking> = FirestoreRecyclerOptions
            .Builder<AdminBooking>().setQuery(query, AdminBooking::class.java).build();

        adAdapter = AdminBookingAdapter(firestoreRecyclerOptions);

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adAdapter
    }

    override fun onStart() {
        super.onStart()
        adAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        adAdapter!!.stopListening()
    }
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