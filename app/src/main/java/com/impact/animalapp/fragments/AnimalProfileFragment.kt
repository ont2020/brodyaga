package com.impact.animalapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.impact.animalapp.R
import com.impact.animalapp.models.Animal
import com.impact.animalapp.models.Global
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [AnimalProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AnimalProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_animal_profile, container, false)
        val navController = findNavController()
        val image = root.findViewById<ImageView>(R.id.image_animal_profile)
        val status = root.findViewById<TextView>(R.id.status_animal_profile_text)
        val stateHealth = root.findViewById<TextView>(R.id.state_health_animal_profile)
        val isChip = root.findViewById<TextView>(R.id.chip_bool_profile)
        val shelter = root.findViewById<TextView>(R.id.shelter_animal_profile_text)
        val contacts = root.findViewById<TextView>(R.id.contacts_animal_profile_text)
        val dateText = root.findViewById<TextView>(R.id.date_profile_text)
        val description = root.findViewById<TextView>(R.id.description_animal_profile_text)
        val editFab = root.findViewById<FloatingActionButton>(R.id.edit_animal_profile_fab)

        if (Global.user?.isWorker?.contains("true")!!) {
            editFab.show()
        } else {
            editFab.hide()
        }
        var animal: Animal? = Global.animal



        Picasso.get().load(animal?.image).into(image)
        status.text = animal?.status
        stateHealth.text = animal?.stateHealth
        isChip.text = animal?.isChip.toString()
        stateHealth.text = animal?.stateHealth
        stateHealth.text = animal?.stateHealth
        shelter.text = animal?.shelter
        contacts.text = animal?.contacts
        dateText.text = animal?.date_require
        description.text = animal?.description



        editFab.setOnClickListener {
            navController.navigate(R.id.action_animalProfileFragment_to_animalProfileEditorFragment)
        }


        return root
    }


}