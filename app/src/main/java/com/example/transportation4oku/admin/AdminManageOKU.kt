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

class AdminManageOKU : AppCompatActivity() {
    private lateinit var  recyclerView: RecyclerView
    private lateinit var amuArrayList : ArrayList<AdminManageOKUModel>
    private lateinit var amuAdapter : AdminManageOKUAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_manage_user)

        val toMain = findViewById<Button>(R.id.toAdminMainBtn)
        toMain.setOnClickListener {
            val intent = Intent(this, AdminMain::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.ManageOKUList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        amuArrayList = arrayListOf()

        amuAdapter = AdminManageOKUAdapter(amuArrayList)

        recyclerView.adapter = amuAdapter
        amuAdapter.setOnItemClickListener(object: AdminManageOKUAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@AdminManageOKU, AdminManageOKUDetail::class.java)
                intent.putExtra("email", amuArrayList[position].email)
                startActivity(intent)
            }
        })
        EventChangeListener()
    }
    private fun EventChangeListener(){
        db = FirebaseFirestore.getInstance()
        db.collection("OKU").
        addSnapshotListener(object: EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    Log.e("Firestore Error",error.message.toString())
                    return
                }

                for (dc : DocumentChange in value?.documentChanges!!){

                    if (dc.type == DocumentChange.Type.ADDED){

                        amuArrayList.add(dc.document.toObject(AdminManageOKUModel::class.java))
                    }
                }
                amuAdapter.notifyDataSetChanged()
            }
        })
    }
}