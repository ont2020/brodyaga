package com.impact.animalapp.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.impact.animalapp.R
import com.impact.animalapp.adapters.AnimalRequestRvAdapter
import com.impact.animalapp.models.Animal
import com.impact.animalapp.models.Global

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [RequestAnimalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RequestAnimalFragment : Fragment() {
    private var animalList = mutableListOf<Animal>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_request_animal, container, false)
        val navController = findNavController()
        animalList = Global.animalList
        val recyclerView = root.findViewById<RecyclerView>(R.id.request_animal_rv)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = AnimalRequestRvAdapter(Global.animalList, navController)
        recyclerView.adapter = adapter



        return root
    }




}