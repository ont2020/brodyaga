package com.impact.animalapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.impact.animalapp.R
import com.impact.animalapp.models.Animal
import com.impact.animalapp.models.Global

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var animalList = mutableListOf<Animal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val btn = root.findViewById<Button>(R.id.button2)
        val btn2 = root.findViewById<Button>(R.id.button3)
        val btn3 = root.findViewById<Button>(R.id.button4)
        val navController = findNavController()



        btn.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_newAnimalFragment)
        }
        btn2.setOnClickListener {
            Global.animalList = animalList
            navController.navigate(R.id.action_mainFragment_to_requestAnimalFragment)
        }

        btn3.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_animalListFragment)
        }
        return root
    }






}