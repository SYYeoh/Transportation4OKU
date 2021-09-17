package com.example.transportation4oku.admin

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
import com.example.transportation4oku.MainActivity
import com.example.transportation4oku.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class AdminLogin : AppCompatActivity() {
    var role: String? = null
    var status: Boolean? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        auth = FirebaseAuth.getInstance()

        val email  = findViewById<EditText>(R.id.AdminEmailLogin)
        val pass  = findViewById<EditText>(R.id.AdminPassLogin)

        val signup = findViewById<TextView>(R.id.AdminSignTV)
        val reset = findViewById<TextView>(R.id.AdminResetBtn)

        val toMain = findViewById<Button>(R.id.toMainAdminBtn)
        val login = findViewById<Button>(R.id.AdminloginBtn)

        signup.setOnClickListener {
            val intent = Intent(this, AdminSignUp::class.java)
            startActivity(intent)
            finish()
        }

        toMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        login.setOnClickListener {
            if (email.text.toString().isEmpty()) {
                email.error = "Please enter email"
                email.requestFocus()
                return@setOnClickListener
            }
            if (pass.text.toString().isEmpty() || pass.length() < 6) {
                pass.error = "Please enter 6 character password"
                pass.requestFocus()
                return@setOnClickListener
            }
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("Admin").document(email.text.toString())
            docRef.addSnapshotListener(this) {
                value, error ->
                role = value?.getString("role")
                Log.d(TAG, role.toString())
            }

            auth.signInWithEmailAndPassword(email.text.toString(), pass.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        if (role != "admin") {
                            Log.d(TAG, role.toString())
                            Toast.makeText(baseContext, "Your role is not Admin",
                                Toast.LENGTH_SHORT).show()
                            return@addOnCompleteListener
                        } else {
                            Toast.makeText(baseContext, "Log in Successfully!",
                                Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, AdminMain::class.java))
                            finish()
                        }
                    } else {
                        Toast.makeText(baseContext, "Log in Failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
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
    }

    companion object {
        private const val TAG = "TAG"
    }
}