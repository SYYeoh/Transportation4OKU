package com.example.transportation4oku.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.transportation4oku.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdminManageCGDetail : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    var name: String? = null
    var email: String? = null
    var ic: String? = null
    var cont: String? = null
    var status: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_manage_cgdetail)
        val db = FirebaseFirestore.getInstance()

        //button
        val toMain = findViewById<Button>(R.id.toAdminMainMCGbtn)
        val banAcc = findViewById<Button>(R.id.BanCGBtn)

        //textview
        val nameTV = findViewById<TextView>(R.id.CGnameAMCG)
        val icTV = findViewById<TextView>(R.id.CGicAMCG)
        val contTV = findViewById<TextView>(R.id.CGcontactAMCG)
        val statusTV = findViewById<TextView>(R.id.CGstatusAMCG)

        //get value from admin manage oku
        val bundle : Bundle?= intent.extras
        val email = bundle!!.getString("email")

        val emailTV = findViewById<TextView>(R.id.CGemailAMCG)
        Log.d("TAG", "email $email")

        emailTV.text = email.toString()

        val docRef = db.collection("CareGiver").document(email.toString())
        docRef.addSnapshotListener(this) {
                value, error ->
            name = value?.getString("name")
            ic  = value?.getString("ic")
            cont = value?.getString("contact")
            status = value?.getBoolean("status").toString()

            nameTV.text = name
            icTV.text = ic
            contTV.text = cont
            statusTV.text = status
        }

        toMain.setOnClickListener {
            val intent = Intent(this, AdminManageCG::class.java)
            startActivity(intent)
            finish()
        }
        banAcc.setOnClickListener {
            if (status == "true") {
                val banRef = db.collection("CareGiver").document(email.toString())
                banRef.update("status", false).addOnSuccessListener {
                    Toast.makeText(this, "This user has been Unbanned!", Toast.LENGTH_SHORT)
                        .show()
                }.addOnFailureListener {
                        e -> Log.d("TAG", "Error ban", e)
                    Toast.makeText(this, "Error occurred!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                val banRef = db.collection("CareGiver").document(email.toString())
                banRef.update("status", true).addOnSuccessListener {
                    Toast.makeText(this, "User has been Banned!", Toast.LENGTH_SHORT)
                        .show()
                }.addOnFailureListener {
                        e -> Log.d("TAG", "Error ban", e)
                    Toast.makeText(this, "Error occurred!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}