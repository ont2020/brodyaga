package com.impact.animalapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.impact.animalapp.R
import com.impact.animalapp.models.Global
import com.impact.animalapp.models.Shelter
import com.squareup.picasso.Picasso

class ShelterInProfileRvAdapter (private val items: MutableList<Shelter>): RecyclerView.Adapter<ShelterInProfileRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.shelter_item, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            //Global.shelter = items[position]

        }

    }


    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nameText = itemView.findViewById<TextView>(R.id.name_shelter_item)
        private var address = itemView.findViewById<TextView>(R.id.address_shelter_item)
        private var image = itemView.findViewById<ImageView>(R.id.shelter_image_item)


        fun  bind(item: Shelter) {
            nameText.text = item.name
            address.text = item.address


            Picasso.get()
                .load(item.image)
                .error(R.drawable.camera)
                .into(image)

        }
    }

    public interface OnСlickListener {
        fun onClick(position: Int)

    }
}