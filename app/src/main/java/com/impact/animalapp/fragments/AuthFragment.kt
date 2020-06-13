package com.impact.animalapp.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.impact.animalapp.MainActivity
import com.impact.animalapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [AuthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AuthFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_auth, container, false)
        val simpleBtn = root.findViewById<Button>(R.id.simple_auth_btn)
        val navController = findNavController()



        simpleBtn.setOnClickListener {
            createSignInIntent(navController)
        }



        return root
    }

    private fun createSignInIntent( navController: NavController) {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            //AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())
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

                goToMainFragment(user)


            } else {

            }
        }
    }

    private fun goToMainFragment(user: FirebaseUser?) {
        val navController = findNavController()
        val bundle = Bundle()

        navController.navigate(R.id.action_authFragment_to_animalListFragment)
    }




}