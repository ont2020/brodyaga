package com.impact.animalapp.worker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.impact.animalapp.MainActivity
import com.impact.animalapp.R
import com.impact.animalapp.models.Global
import com.impact.animalapp.models.User

class WorkerLoginActivity : AppCompatActivity() {
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_login)

        val emailText = findViewById<TextInputEditText>(R.id.email_login_text)
        val passwordText = findViewById<TextInputEditText>(R.id.pass_login_text)
        val signInBtn = findViewById<Button>(R.id.sign_in_btn)
        val regBtn = findViewById<Button>(R.id.sign_up_btn)


        signInBtn.setOnClickListener {
            email = emailText.text.toString()
            password = passwordText.text.toString()
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                signIn(email.toString(), password.toString())
            } else {
                Toast.makeText(this, "Зполните поля", Toast.LENGTH_LONG).show()
            }
        }
        regBtn.setOnClickListener {

            startActivity(Intent(this, WorkerRegActivity::class.java))
        }


    }

    private fun signIn(email: String, password: String) {
        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    var currentUser = firebaseAuth.currentUser
                    if (currentUser != null) {
                        getUser(currentUser)
                    }
                }
            }.addOnFailureListener {
                Log.d("SignInFail", it.message.toString())
            }
    }

    private fun getUser(currentUser: FirebaseUser ) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(currentUser.email.toString())
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    it.addOnSuccessListener {
                        var user = User(
                            it["id"].toString(),
                            it["name"].toString(),
                            it["organization_name"].toString(),
                            it["is_worker"].toString(),
                            it["email"].toString()
                        )
                        Global.user = user
                        Global.isWorker = true
                        startActivity(Intent(this, MainActivity::class.java))


                    }

                }
            }
    }
}