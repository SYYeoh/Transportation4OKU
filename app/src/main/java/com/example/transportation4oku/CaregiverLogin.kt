package com.example.transportation4oku

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.transportation4oku.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CaregiverLogin : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caregiver_login)
        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.CareGiverEmailLogin)
        val pass = findViewById<EditText>(R.id.CGPassLogin)

        val loginBtn = findViewById<Button>(R.id.CGloginBtn)
        val toMain = findViewById<Button>(R.id.toMainCGBtn)

        val registerbtn = findViewById<TextView>(R.id.CGSignTV)
        val reset = findViewById<TextView>(R.id.CGResetBtn)

        registerbtn.setOnClickListener {
            val intent = Intent(this, CGSignUp::class.java)
            startActivity(intent)
            finish()
        }
        toMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginBtn.setOnClickListener {
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
            auth.signInWithEmailAndPassword(email.text.toString(), pass.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(baseContext, "Log in Successfully!",
                            Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, CGMain::class.java))
                        finish()
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
}