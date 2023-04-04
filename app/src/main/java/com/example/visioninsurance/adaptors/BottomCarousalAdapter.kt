package com.example.visioninsurance.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.visioninsurance.R
import com.example.visioninsurance.model.BottomCarousalModel
import com.example.visioninsurance.model.MiddleCarousalModel

class BottomCarousalAdapter(searchList: List<BottomCarousalModel>)  : RecyclerView.Adapter<BottomCarousalAdapter.BottomCarousalViewHolder>(){

    private var list: List<BottomCarousalModel> = searchList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomCarousalViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.dashboard_recyclerview_3, parent, false)

        return BottomCarousalViewHolder(v)
    }

    override fun onBindViewHolder(holder: BottomCarousalViewHolder, position: Int) {

        holder.image1.setImageResource(list[position].getImage())
        holder.text1.text = list[position].getTitle()
        holder.text2.text = list[position].getSubTitle()
    }

    override fun getItemCount(): Int {

        return list.size

    }
    class BottomCarousalViewHolder(view: View): RecyclerView.ViewHolder(view){

        val image1: ImageView = itemView.findViewById(R.id.imageView)
        val text1 : TextView = itemView.findViewById(R.id.card_text1)
        val text2 : TextView = itemView.findViewById(R.id.card_text2)

    }
}