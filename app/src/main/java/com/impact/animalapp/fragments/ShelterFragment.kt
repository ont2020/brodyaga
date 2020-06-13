package com.impact.animalapp.fragments

import android.os.Bundle
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
import com.impact.animalapp.adapters.AllAnimalRvAdapter
import com.impact.animalapp.adapters.ShelterRvAdapter
import com.impact.animalapp.models.Animal
import com.impact.animalapp.models.Global
import com.impact.animalapp.models.Shelter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match


/**
 * A simple [Fragment] subclass.
 * Use the [ShelterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShelterFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    private val shelterRef = db.collection("shelters")
    private var shelterList = mutableListOf<Shelter>()
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_shelter, container, false)
        val navController = findNavController()
        recyclerView = root.findViewById<RecyclerView>(R.id.shelter_rv)
        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = ShelterRvAdapter(shelterList, navController)
        //adapter.notifyDataSetChanged()
        recyclerView?.adapter = adapter


        getData()
        return root
    }


    private fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            getShelterList()
            delay(2000)
        }
    }

    suspend fun getShelterList(): MutableList<Shelter> {
        var firebaseFirestore = FirebaseFirestore.getInstance()
            .collection("shelters")
            .get()
            .addOnSuccessListener {
                Log.d("LoadedRequire", it.documents.size.toString())
                val documentList = it.documents
                for (document in it.documents) {
                    var shelter = Shelter(
                        document.id,
                        document["name"].toString(),
                        document["address"].toString(),
                        document["contacts"].toString(),
                        document["description"].toString(),
                        document["schedule"].toString(),
                        document["image"].toString()

                    )

                    shelterList.add(shelter)

                    //Global.animalList = animalList
                }
                recyclerView?.adapter?.notifyDataSetChanged()
                Log.d("Shelter", shelterList.size.toString())

            }
            .addOnFailureListener {
                Log.d("LoadFail", it.message.toString())
            }
        return shelterList
    }

}