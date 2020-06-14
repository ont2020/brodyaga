package com.impact.animalapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.impact.animalapp.R
import com.impact.animalapp.adapters.ShelterInProfileRvAdapter
import com.impact.animalapp.adapters.ShelterRvAdapter
import com.impact.animalapp.models.Global
import com.impact.animalapp.models.Shelter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AnimalProfileEditorFragment : Fragment() {
    private var shelterRv: RecyclerView? = null
    private var shelterList = mutableListOf<Shelter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_animal_profile_editor, container, false)
        var navController = findNavController()
        var imageEditor = root.findViewById<ImageView>(R.id.image_animal_editor)
        var typeText = root.findViewById<TextView>(R.id.type_editor)
        var contactsText = root.findViewById<TextInputEditText>(R.id.contacts_editor)
        var isChip = root.findViewById<CheckBox>(R.id.isChipCheckBox)
        var healthText = root.findViewById<TextInputEditText>(R.id.state_health_editor_text)
        var statusRadio = root.findViewById<RadioGroup>(R.id.radioEditGroup)
        var descriptionText = root.findViewById<TextInputEditText>(R.id.description_editor_text)
        var dateText = root.findViewById<TextView>(R.id.date_editor_text)
        var acceptEditFab = root.findViewById<FloatingActionButton>(R.id.accept_edit_fab)
        shelterList = Global.shelterList

        shelterRv = root.findViewById<RecyclerView>(R.id.shelter_rv)
        shelterRv?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = ShelterInProfileRvAdapter(shelterList)
        shelterRv?.adapter = adapter

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

        statusRadio.setOnCheckedChangeListener { radioGroup, i ->
            RadioGroup.OnCheckedChangeListener { radioGroup, i ->
                val radioButton = root.findViewById<RadioButton>(i)
                when (i) {
                    0 -> animal?.status = "В обработке"
                    1 -> animal?.status = "В приюте"
                    2 -> animal?.status = "На улице"
                    3 -> animal?.status = "Найден хозяин"
                }
            }
        }


        var radioButton1 = root.findViewById<RadioButton>(R.id.radio1_edit)
        var radioButton2 = root.findViewById<RadioButton>(R.id.radio2_edit)
        var radioButton3 = root.findViewById<RadioButton>(R.id.radio3_edit)
        var radioButton4 = root.findViewById<RadioButton>(R.id.radio4_edit)
        when (status) {
            "В обработке" -> statusRadio.check(R.id.radio1_edit)
            "В приюте" -> statusRadio.check(R.id.radio2_edit)
            "На улице" -> statusRadio.check(R.id.radio3_edit)
            "Найден хозяин" -> statusRadio.check(R.id.radio4_edit)
        }
        isChip.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                Global.animal?.isChip = true.toString()
            } else {
                Global.animal?.isChip = false.toString()
            }
        }


        acceptEditFab.setOnClickListener {
            var contacts = contactsText.text.toString()
            var health = healthText.text.toString()
            var description = descriptionText.text.toString()
            var statusCurrent = animal?.status
            var isChipCurrent = Global.animal?.isChip
            var docId = Global.animal?.animalDocId
            if (docId != null) {
                writeData(navController, contacts, health, description, statusCurrent, isChipCurrent, docId)
            }
        }


        return root
    }

    private fun writeData(nav: NavController, contacts: String, health: String, description: String, status: String?, chip: String?, docId: String) {
        var hashMap = hashMapOf<String, Any>(
            "type" to Global.animal?.type!!,
            "description" to description,
            "contacts" to contacts,
            "photo" to Global.animal?.image!!,
            "status" to status!!,
            "date" to Global.animal?.date_require!!,
            "latitude" to Global.animal?.latitude!!,
            "longitude" to Global.animal?.longitude!!,
            "shelter" to Global.selectedShelter!!,
            "state_health" to health,
            "chip" to chip?.toBoolean()!!
        )
        var db = FirebaseFirestore.getInstance()
        db.collection("animals")
            .document(docId)
            .set(hashMap)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    it.addOnSuccessListener {
                        nav.navigate(R.id.action_animalProfileEditorFragment_to_animalProfileFragment)
                    }.addOnFailureListener {
                        Log.d("Fail", it.message.toString())
                    }
                }
            }
    }


}