package com.impact.animalapp.fragments

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.impact.animalapp.R
import com.impact.animalapp.adapters.AllAnimalRvAdapter
import com.impact.animalapp.adapters.AnimalRequestRvAdapter
import com.impact.animalapp.models.Animal
import com.impact.animalapp.models.Global
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [AnimalListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AnimalListFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    private val animalRef = db.collection("animals")
    private var animalList = mutableListOf<Animal>()
    private var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_animal_list, container, false)
        val navController = findNavController()
        recyclerView = root.findViewById<RecyclerView>(R.id.animal_main_rv)
        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = AllAnimalRvAdapter(animalList, navController)
        //adapter.notifyDataSetChanged()
        recyclerView?.adapter = adapter

        getData()

        return root
    }

    private fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            getAnimalList()
            delay(2000)
        }
    }

    suspend fun getAnimalList(): MutableList<Animal> {
        var firebaseFirestore = FirebaseFirestore.getInstance()
            .collection("animals")
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
                        document["description"].toString()

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
    /*suspend fun setUpRecyclerView(animalList: MutableList<Animal>) {
        val query: Query = animalRef.orderBy("priority", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<Animal> = Builder<Animal>()
            .setQuery(query, Note::class.java)
            .build()
    }*/



}