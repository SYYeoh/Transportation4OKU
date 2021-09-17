package com.example.transportation4oku.caregiver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.transportation4oku.MainActivity
import com.example.transportation4oku.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class CGSignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cgsign_up)
        auth = FirebaseAuth.getInstance()

        val name = findViewById<EditText>(R.id.cgName)
        val email = findViewById<EditText>(R.id.cgEmail)
        val pass = findViewById<EditText>(R.id.cgPass)
        val confirmPass = findViewById<EditText>(R.id.cgConfirmPass)
        val contact = findViewById<EditText>(R.id.cgContactNo)
        val ic = findViewById<EditText>(R.id.cgIC)

        val tologin = findViewById<Button>(R.id.tologinCG)
        val register = findViewById<Button>(R.id.cgRegister)

        tologin.setOnClickListener {
            val intent = Intent(this, CaregiverLogin::class.java)
            startActivity(intent)
            finish()
        }

        register.setOnClickListener {
            if (name.text.toString().isEmpty()) {
                name.error = "Please enter name"
                name.requestFocus()
                return@setOnClickListener
            }
            if (email.text.toString().isEmpty()) {
                email.error = "Please enter email"
                email.requestFocus()
                return@setOnClickListener
            }
            if (pass.text.toString().isEmpty()) {
                pass.error = "Please enter 6 character password"
                pass.requestFocus()
                return@setOnClickListener
            }
            if (confirmPass.text.toString().isEmpty()) {
                confirmPass.error = "Please enter 6 character confirm password"
                confirmPass.requestFocus()
                return@setOnClickListener
            }
            if (pass.text.toString() != confirmPass.text.toString()) {
                pass.error = "Password and Confirm Password is not identical!"
                pass.requestFocus()
                return@setOnClickListener
            }
            if (contact.text.toString().isEmpty()) {
                contact.error = "Please enter contact number"
                contact.requestFocus()
                return@setOnClickListener
            }

            if (ic.text.toString().isEmpty() || ic.length() < 10) {
                ic.error = "Please enter 10 number IC number"
                ic.requestFocus()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = Firebase.auth.currentUser
                        val fStore = FirebaseFirestore.getInstance()
                        val cgDetail = hashMapOf(
                            "name" to name.text.toString(),
                            "email" to email.text.toString(),
                            "contact" to contact.text.toString(),
                            "ic" to ic.text.toString(),
                            "role" to "cg",
                            "status" to false
                        )
                        val docRef = fStore.collection("CareGiver").document(email.text.toString())
                            docRef.set(cgDetail).addOnSuccessListener {
                            document ->
                                Toast.makeText(baseContext, "Registered Successfully!",
                                    Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, CGMain::class.java))
                                finish()
                        }.addOnFailureListener { e -> Log.w("TAG", "Error register cg")
                        return@addOnFailureListener}


                    } else {
                        Toast.makeText(baseContext, "Register failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null ) {
            val intent = Intent(this, CGMain::class.java)
            startActivity(intent)
            finish()
        }else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}