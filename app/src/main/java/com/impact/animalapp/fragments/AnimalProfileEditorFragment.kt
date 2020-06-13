package com.impact.animalapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.impact.animalapp.R
import com.impact.animalapp.models.Global
import com.squareup.picasso.Picasso


class AnimalProfileEditorFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_animal_profile_editor, container, false)
        var imageEditor = root.findViewById<ImageView>(R.id.image_animal_editor)
        var typeText = root.findViewById<TextView>(R.id.type_editor)
        var contactsText = root.findViewById<TextInputEditText>(R.id.contacts_editor)
        var isChip = root.findViewById<CheckBox>(R.id.isChipCheckBox)
        var healthText = root.findViewById<TextInputEditText>(R.id.image_animal_editor)
        var statusRadio = root.findViewById<RadioGroup>(R.id.radioEditGroup)
        var descriptionText = root.findViewById<TextInputEditText>(R.id.radioEditGroup)
        var dateText = root.findViewById<TextView>(R.id.radioEditGroup)
        var acceptEditFab = root.findViewById<FloatingActionButton>(R.id.accept_edit_fab)

        var animal = Global.animal

        Picasso.get()
            .load(animal?.image)
            .into(imageEditor)
        typeText.text = animal?.type
        contactsText.hint = animal?.contacts
        var chip = "true"
        var chipCurrent = animal?.isChip
        if (chip == chipCurrent) {
            isChip.isChecked = true
        } else {
            isChip.isChecked = false
        }
        healthText.hint = animal?.stateHealth
        descriptionText.hint = animal?.description
        dateText.text = animal?.date_require
        var status = animal?.status

        
        return root
    }
}