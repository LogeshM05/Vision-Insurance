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

class MiddleCarousalAdapter(serviceList: List<MiddleCarousalModel>) : RecyclerView.Adapter<MiddleCarousalAdapter.MiddleCarousalViewHolder>(){

    private var list: List<MiddleCarousalModel> = serviceList

    private var onClickListener: OnClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiddleCarousalViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.dashboard_recyclerview_2, parent, false)

        return MiddleCarousalViewHolder(v)
    }

    override fun onBindViewHolder(holder: MiddleCarousalViewHolder, position: Int) {

        val item = list[position]

        holder.image1.setImageResource(list[position].getImage())
        holder.text1.text = list[position].getTitle()
        holder.text2.text = list[position].getSubTitle()

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item )
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: MiddleCarousalModel)
    }

    override fun getItemCount(): Int {
        return list.size

    }

    class MiddleCarousalViewHolder(view: View): RecyclerView.ViewHolder(view){

        val image1: ImageView = itemView.findViewById(R.id.imageView)
        val text1 : TextView = itemView.findViewById(R.id.card_text1)
        val text2 : TextView = itemView.findViewById(R.id.card_text2)

    }
}