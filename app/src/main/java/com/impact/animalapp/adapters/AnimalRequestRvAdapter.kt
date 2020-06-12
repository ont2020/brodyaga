package com.impact.animalapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.impact.animalapp.R
import com.impact.animalapp.models.Animal
import com.squareup.picasso.Picasso

class AnimalRequestRvAdapter (private val items: MutableList<Animal>, var navController: NavController): RecyclerView.Adapter<AnimalRequestRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.animal_request_item, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            navController.navigate(R.id.action_requestAnimalFragment_to_animalProfileFragment)
        }

    }


    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var typeText = itemView.findViewById<TextView>(R.id.type_animal_request_item)
        private var contactsText = itemView.findViewById<TextView>(R.id.contacts_animal_request_item)
        private var image = itemView.findViewById<ImageView>(R.id.image_animal_request_item)


        fun  bind(item: Animal) {
            typeText.text = item.type
            contactsText.text = item.contacts

            Picasso.get()
                .load(item.image)
                .into(image)
        }
    }

    public interface OnСlickListener {
        fun onClick(position: Int)

    }
}

