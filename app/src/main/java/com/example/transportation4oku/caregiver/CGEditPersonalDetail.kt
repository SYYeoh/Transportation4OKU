package com.example.transportation4oku.caregiver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.transportation4oku.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CGEditPersonalDetail : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    var name: String? = null
    var cont: String? = null
    var ic: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cgedit_personal_detail)
        auth = FirebaseAuth.getInstance()
        //edittext
        val nameET = findViewById<EditText>(R.id.namecgepd)
        val contET = findViewById<EditText>(R.id.contcgepd)
        val icET = findViewById<EditText>(R.id.iccgepd)

        //button
        val back = findViewById<Button>(R.id.backcgepdBtn)
        val save = findViewById<Button>(R.id.savecgepd)

        //get from CGPersonalDetail
        val bundle : Bundle?= intent.extras
        val name = bundle!!.getString("name")
        val cont = bundle!!.getString("cont")
        val ic = bundle!!.getString("ic")
        val email = bundle!!.getString("email")

        nameET.setText(name)
        contET.setText(cont)
        icET.setText(ic)

        Log.d("TAG", "CFEditPersonalDetail: $name $cont $ic $email")

        back.setOnClickListener {
            val intent = Intent(this, CGMain::class.java)
            startActivity(intent)
            finish()
        }

        save.setOnClickListener {
            if (nameET.text.toString().isEmpty()) {
                nameET.error = "Please enter name"
                nameET.requestFocus()
                return@setOnClickListener
            }
            if (contET.text.toString().isEmpty()) {
                contET.error = "Please enter contact number"
                contET.requestFocus()
                return@setOnClickListener
            }

            if (icET.text.toString().isEmpty() || icET.length() < 10) {
                icET.error = "Please enter 10 number IC number"
                icET.requestFocus()
                return@setOnClickListener
            }

            db = FirebaseFirestore.getInstance()
            val saveRef = db.collection("CareGiver").document(email.toString())
            saveRef.update("name", nameET.text.toString(),
                "contact", contET.text.toString(), "ic", icET.text.toString())
                .addOnSuccessListener {
                    Toast.makeText(this, "You has Updated your Detail!", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, CGPersonalDetail::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                        e -> Log.d("TAG", "Error edit", e)
                    Toast.makeText(this, "Error occurred!", Toast.LENGTH_SHORT)
                        .show()
                }
        }



    }
}