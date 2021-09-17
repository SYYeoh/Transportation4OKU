package com.example.transportation4oku.oku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.transportation4oku.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase

class OKUBooking : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var okuArrayList: ArrayList<OKUBookingModel>
    private lateinit var okuAdapter: OKUBookingAdapter
    private lateinit var db: FirebaseFirestore

    private var email: String? = null
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okubooking)

        recyclerView = findViewById(R.id.recycleView1)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        okuArrayList = arrayListOf()
        okuAdapter = OKUBookingAdapter(okuArrayList)
        recyclerView.adapter = okuAdapter
        okuAdapter.setOnItemClickListener(object : OKUBookingAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                //Toast.makeText(this@MainActivity,"You Clicked on item no. $position",Toast.LENGTH_LONG).show()
                val intent = Intent(this@OKUBooking, OKUBookingDetail::class.java)
                intent.putExtra("id", okuArrayList[position].id)
                startActivity(intent)
            }

        })
        EventChangeListener()
    }

    private fun EventChangeListener() {
        val user = Firebase.auth.currentUser
        user?.let {
            email = user.email
        }
        db = FirebaseFirestore.getInstance()
        val docRef = db.collection("OKU").document(email.toString())
        docRef.addSnapshotListener(this) { value, error ->
            name = value?.getString("name")
            Log.d("TAG", "$name")
            db.collection("Booking Detail")
                .whereEqualTo("oku", "$name")
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

                                okuArrayList.add(dc.document.toObject(OKUBookingModel::class.java))
                            }
                        }
                        okuAdapter.notifyDataSetChanged()
                    }
                })
        }
    }
}