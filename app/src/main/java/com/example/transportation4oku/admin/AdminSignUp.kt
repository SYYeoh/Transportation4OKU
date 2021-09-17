package com.example.transportation4oku.admin

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
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore

class AdminSignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_sign_up)

        auth = FirebaseAuth.getInstance()

        val name = findViewById<EditText>(R.id.adminName)
        val email = findViewById<EditText>(R.id.adminEmail)
        val pass = findViewById<EditText>(R.id.adminPass)
        val confirmPass = findViewById<EditText>(R.id.adminConfirmPass)
        val contact = findViewById<EditText>(R.id.adminContact)
        val ic = findViewById<EditText>(R.id.adminIC)

        val tologin = findViewById<Button>(R.id.tologinadmin)
        val register = findViewById<Button>(R.id.adminRegister)

        tologin.setOnClickListener {
            val intent = Intent(this, AdminLogin::class.java)
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
                pass.error = "Please enter password"
                pass.requestFocus()
                return@setOnClickListener
            }
            if (confirmPass.text.toString().isEmpty()) {
                confirmPass.error = "Please enter confirm password"
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
                        val AdminDetail = hashMapOf(
                            "name" to name.text.toString(),
                            "email" to email.text.toString(),
                            "contact" to contact.text.toString(),
                            "ic" to ic.text.toString(),
                            "role" to "admin".toString()
                        )
                        val docRef = fStore.collection("Admin").document(email.text.toString())
                        docRef.set(AdminDetail).addOnSuccessListener {
                                document ->
                            Log.d("TAG", "Admin added " + name.text.toString())
                            Toast.makeText(baseContext, "Registered Successfully!",
                                Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener { e -> Log.w("TAG", "Error register admin") }

                        startActivity(Intent(this, AdminMain::class.java))
                        finish()
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
            val intent = Intent(this, AdminMain::class.java)
            startActivity(intent)
            finish()
        }else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}