package com.example.transportation4oku

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.transportation4oku.caregiver.CGEditPersonalDetail
import com.example.transportation4oku.caregiver.CGMain
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class OKUPersonalDetail : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    var name: String? = null
    var cont: String? = null
    var ic: String? = null
    var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okupersonal_detail)
        val db = FirebaseFirestore.getInstance()
        //textview
        val nameTV = findViewById<TextView>(R.id.nameokupd)
        val emailTV = findViewById<TextView>(R.id.emailokupd)
        val contTV = findViewById<TextView>(R.id.contactokupd)
        val icTV  = findViewById<TextView>(R.id.icokupd)

        //button
        val toMain = findViewById<Button>(R.id.backokupd)
        val edit = findViewById<Button>(R.id.editokupd)
        val reset = findViewById<Button>(R.id.resetokupd)
        val lo = findViewById<Button>(R.id.OKULogOutBtn)

        val user = Firebase.auth.currentUser
        user?.let {
            nameTV.text = user.displayName
            emailTV.text = user.email
        }

        val docRef = db.collection("OKU").document(emailTV.text.toString())
        docRef.addSnapshotListener(this) {
                value, error ->
            name = value?.getString("name")
            ic  = value?.getString("ic")
            cont = value?.getString("contact")

            nameTV.text = name
            icTV.text = ic
            contTV.text = cont
        }

        toMain.setOnClickListener {
            val intent = Intent(this, OKUMain::class.java)
            startActivity(intent)
            finish()
        }
        edit.setOnClickListener {
            val intent = Intent(this, OKUEditPersonalDetail::class.java)
            intent.putExtra("name",name)
            intent.putExtra("ic",ic)
            intent.putExtra("cont",cont)
            intent.putExtra("email", emailTV.text.toString())
            Log.d("TAG", "CFEditPersonalDetail: $name $cont $ic $email")
            startActivity(intent)
            finish()
        }

        reset.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Reset Password?")
            builder.setMessage("Enter Your Email to Receive Reset Link.")
            val input = EditText(this)
            input.setHint("Enter Email")
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)
            builder.setPositiveButton("Yes"){dialogInterface, which ->
                var resetEmail = input.text.toString()
                Firebase.auth.sendPasswordResetEmail(resetEmail)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(baseContext, "Reset Link has Sent to Your Email."
                                , Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(baseContext, "Error has occurred! Reset Link is Not Sent!"
                                , Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            builder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener{ dialog, which -> dialog.cancel() })
            builder.show()
        }

        lo.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}