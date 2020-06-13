package com.impact.animalapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    private fun createSignInIntent( navController: NavController) {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())
        //AuthUI.IdpConfig.PhoneBuilder().build(),
        //AuthUI.IdpConfig.FacebookBuilder().build(),
        //AuthUI.IdpConfig.TwitterBuilder().build())

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), 123)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {

                val user = FirebaseAuth.getInstance().currentUser

                startActivity(Intent(this, MainActivity::class.java))


            } else {

            }
        }
    }
}