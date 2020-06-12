package com.impact.animalapp.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.impact.animalapp.R

class TypeRvAdapter (private val items: MutableList<String>): RecyclerView.Adapter<TypeRvAdapter.ViewHolder>() {
    private var posSelected = -1
    private var listener: OnСlickListener? = null


    fun setListener(listener: OnСlickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.type_item, parent, false))



    override fun getItemCount(): Int = items.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(items[position])
        if(posSelected == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FF9800"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
        }
        holder.itemView.setOnClickListener {
            posSelected = position
            //Global.selectedType = items[position]
            //Toast.makeText(context, position, Toast.LENGTH_LONG).show()
            Log.d("Click", position.toString())

            notifyDataSetChanged()

        }

    }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imageType = itemView.findViewById<ImageView>(R.id.image_type)
        private var textType = itemView.findViewById<TextView>(R.id.type_name_text)

        fun  bind(item: String) {
            imageType.setImageResource(R.drawable.dog)
            textType.text = item
        }
    }

    public interface OnСlickListener {
        fun onClick(position: Int)

    }
}