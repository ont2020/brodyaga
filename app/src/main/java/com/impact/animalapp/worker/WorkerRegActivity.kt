package com.impact.animalapp.worker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.impact.animalapp.R

class WorkerRegActivity : AppCompatActivity() {
    private var email: String? = null
    private var password: String? = null
    private var name: String? = null
    private var organization: String? = null
    private var password2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_reg)
        val organizationText = findViewById<TextInputEditText>(R.id.organization_name_text)
        val emailText = findViewById<TextInputEditText>(R.id.email_reg_text)
        val nameText = findViewById<TextInputEditText>(R.id.name_reg_text)
        val passText = findViewById<TextInputEditText>(R.id.pass_reg_text)
        val pass2Text = findViewById<TextInputEditText>(R.id.pass2_reg_text)
        val regBtn = findViewById<Button>(R.id.sign_up_btn)

        regBtn.setOnClickListener {
            email = emailText.text.toString()
            name = nameText.text.toString()
            organization = organizationText.text.toString()
            password = passText.text.toString()
            password2 = pass2Text.text.toString()
            if(!name.isNullOrEmpty() && !email.isNullOrEmpty() && !organization.isNullOrEmpty() && !password.isNullOrEmpty() && !password2.isNullOrEmpty() ) {
                if (password == password2) {
                    signUp(email.toString(), name.toString(), organization.toString(), password.toString())
                } else {
                    Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun signUp(email: String, name: String, organization: String, password: String) {
        val userAuth = FirebaseAuth.getInstance()
        userAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    writeNewUser(userAuth.currentUser!!, email, name, organization, password)
                }
            }
            .addOnFailureListener {
                Log.d("RegFail", it.message.toString())
            }
    }
    private fun writeNewUser(currentUser: FirebaseUser, email: String, name: String, organization: String, password: String) {
        var hashMap = hashMapOf<String, Any>(
            "name" to name,
            "email" to email,
            "id" to currentUser.uid,
            "organization_name" to organization,
            "isWorker" to true
        )
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(email)
            .set(hashMap)
            .addOnSuccessListener {
                Log.d("RegSuccess", true.toString())
                startActivity(Intent(this, WorkerLoginActivity::class.java))

            }.addOnFailureListener {
                Log.d("RegFail", it.message.toString())
                Toast.makeText(this, "Ошибка в регистрации", Toast.LENGTH_LONG).show()
            }
    }
}