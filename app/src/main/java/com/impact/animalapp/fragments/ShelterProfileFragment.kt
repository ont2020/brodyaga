package com.impact.animalapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.impact.animalapp.R
import com.impact.animalapp.models.Global
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [ShelterProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShelterProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_shelter_profile, container, false)

        val image = root.findViewById<ImageView>(R.id.image_shelter_profile)
        val name = root.findViewById<TextView>(R.id.name_shelter_text)
        val address = root.findViewById<TextView>(R.id.address_shelter_profile_text)
        val schedule = root.findViewById<TextView>(R.id.schedule_shelter_profile)
        val contacts = root.findViewById<TextView>(R.id.contacts_shelter_profile)
        val description = root.findViewById<TextView>(R.id.description_shelter_profile_text)
        var shelter = Global.shelter

        Picasso.get().load(shelter?.image).into(image)
        name.text = shelter?.name
        address.text = shelter?.address
        schedule.text = shelter?.schedule
        contacts.text = shelter?.contacts
        description.text = shelter?.description
        return root
    }

}