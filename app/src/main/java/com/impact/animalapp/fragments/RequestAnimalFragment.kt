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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.impact.animalapp.R
import com.impact.animalapp.adapters.AnimalRequestRvAdapter
import com.impact.animalapp.models.Animal
import com.impact.animalapp.models.Global
import kotlinx.coroutines.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [RequestAnimalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RequestAnimalFragment : Fragment() {
    private var animalList = mutableListOf<Animal>()
    private var recyclerView: RecyclerView? = null
    private val db = FirebaseFirestore.getInstance()
    private val animalRef = db.collection("animals")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_request_animal, container, false)
        val navController = findNavController()
        recyclerView = root.findViewById<RecyclerView>(R.id.request_animal_rv)
        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = AnimalRequestRvAdapter(animalList, navController)
        recyclerView?.adapter = adapter
        val newAnimalFab = root.findViewById<FloatingActionButton>(R.id.new_animal_add_fab)

        if (Global.user?.isWorker.equals("true")) {
            newAnimalFab.show()
        } else {
            newAnimalFab.hide()
        }

        newAnimalFab.setOnClickListener {
            navController.navigate(R.id.action_requestAnimalFragment_to_newAnimalFragment)
        }

        getData()


        return root
    }


    private fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            async {
                getAnimalList()
            }.await()

        }
    }

    suspend fun getAnimalList(): MutableList<Animal> {
        var firebaseFirestore = FirebaseFirestore.getInstance()
            .collection("animals")
            .whereEqualTo("status", "В обработке")
            .get()
            .addOnSuccessListener {
                Log.d("LoadedRequire", it.documents.size.toString())
                val documentList = it.documents
                for (document in it.documents) {
                    var animal = Animal(
                        document["type"].toString(),
                        document["date"].toString(),
                        document["contacts"].toString(),
                        document["shelter"].toString(),
                        document["status"].toString(),
                        document["photo"].toString(),
                        document["state_health"].toString(),
                        document["chip"].toString(),
                        document["description"].toString(),
                        document["latitude"].toString(),
                        document["longitude"].toString()

                    )

                    animalList.add(animal)

                    Global.animalList = animalList
                }
                recyclerView?.adapter?.notifyDataSetChanged()
                Log.d("Animal", animalList.size.toString())

            }
            .addOnFailureListener {
                Log.d("LoadFail", it.message.toString())
            }
        return animalList
    }

   /* private fun getAnimal() {
        var firebaseFirestore = FirebaseFirestore.getInstance()
            .collection("animals")
            .whereEqualTo("status", "В обработке")
            .get()
            .addOnSuccessListener {
                Log.d("LoadedRequire", it.documents.size.toString())
                val documentList = it.documents
                for (document in it.documents) {
                    var animal = Animal(
                        document["type"].toString(),
                        document["date"].toString(),
                        document["contacts"].toString(),
                        document["shelter"].toString(),
                        document["status"].toString(),
                        document["photo"].toString()
                    )

                    animalList.add(animal)
                    recyclerView?.adapter?.notifyDataSetChanged()
                }
                Log.d("Animal", animalList.size.toString())

            }
            .addOnFailureListener {
                Log.d("LoadFail", it.message.toString())
            }
    }*/




}