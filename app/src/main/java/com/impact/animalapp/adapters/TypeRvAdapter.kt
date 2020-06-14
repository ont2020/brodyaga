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
import com.impact.animalapp.models.Global

class TypeRvAdapter (private val items: MutableList<String>, var img: MutableList<Int>): RecyclerView.Adapter<TypeRvAdapter.ViewHolder>() {
    private var posSelected = -1
    private var listener: OnСlickListener? = null
    private var positionGlobal: Int = -1


    fun setListener(listener: OnСlickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.type_item, parent, false))



    override fun getItemCount(): Int = items.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(items[position], img[position])

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
            Global.selectedType = items[position]

            notifyDataSetChanged()

        }

    }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imageType = itemView.findViewById<ImageView>(R.id.image_type)
        private var textType = itemView.findViewById<TextView>(R.id.type_name_text)

        fun  bind(item: String, img: Int) {
            imageType.setImageResource(img)
            textType.text = item
        }
    }

    public interface OnСlickListener {
        fun onClick(position: Int)

    }
}