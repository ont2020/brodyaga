package com.impact.animalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navView: BottomNavigationView = findViewById(R.id.bottomNavView)
        showBottomNavigationView(navView)
        val navController = findNavController(R.id.fragment_host)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.animalListFragment, R.id.requestAnimalFragment, R.id.shelterFragment
            )
        )

        navView.setupWithNavController(navController)



    }

    fun hideBottomNavigationView(view: BottomNavigationView) {
        view.clearAnimation();
        view.animate().translationY(view.getHeight().toFloat()).setDuration(300);
    }

    fun showBottomNavigationView(view: BottomNavigationView) {
        view.clearAnimation();
        view.animate().translationY(0F).setDuration(300);
    }

}