package com.example.transportation4oku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase

class OKUviewBooking : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var okuArrayList: ArrayList<OKUviewBookingModel>
    private lateinit var okuAdapter: OKUviewBookingAdapter
    private lateinit var db: FirebaseFirestore

    var email: String? = null
    var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okuview_booking)

        //button
        val toMain = findViewById<Button>(R.id.toOKUMainBtn)
        toMain.setOnClickListener {
            val intent = Intent(this, OKUMain::class.java)
            startActivity(intent)
        }

        val user = Firebase.auth.currentUser
        user?.let {
            email = user.email
        }
        db = FirebaseFirestore.getInstance()

        recyclerView = findViewById(R.id.OKUBookingList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        okuArrayList = arrayListOf()
        okuAdapter = OKUviewBookingAdapter(okuArrayList)
        okuAdapter.setOnItemClickListener(object : OKUviewBookingAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@OKUviewBooking, OKUviewBookingDetail::class.java)
                intent.putExtra("id", okuArrayList[position].id)
                startActivity(intent)
            }
        })
        /*val docRef = db.collection("OKU").document(email.toString())
        docRef.addSnapshotListener(this) {
                value, error ->
            name = value?.getString("name")
            Log.d("TAG", "email $email name $name")
        }*/
        EventChangeListener()

    }

    private fun EventChangeListener() {
        //cant get name to query for the OKU booking list
        db = FirebaseFirestore.getInstance()
        val docRef = db.collection("OKU").document(email.toString())
        docRef.addSnapshotListener(this) { value, error ->
            name = value?.getString("name")
            Log.d("TAG", "EventChangeListener $email $name")

            db.collection("Booking Detail").whereEqualTo("oku", name.toString())
                .addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if (error != null) {
                            Log.e("Firestore Error", error.message.toString())
                            return
                        }

                        for (dc: DocumentChange in value?.documentChanges!!) {

                            if (dc.type == DocumentChange.Type.ADDED) {

                                okuArrayList.add(dc.document.toObject(OKUviewBookingModel::class.java))
                            }
                        }
                        okuAdapter.notifyDataSetChanged()
                    }
                })
        }
    }
}