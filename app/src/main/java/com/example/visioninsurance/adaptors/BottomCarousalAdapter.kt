package com.example.visioninsurance.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.visioninsurance.R
import com.example.visioninsurance.model.BottomCarousalModel
import com.example.visioninsurance.model.MiddleCarousalModel

class BottomCarousalAdapter(searchList: List<BottomCarousalModel>)  : RecyclerView.Adapter<BottomCarousalAdapter.BottomCarousalViewHolder>(){

    private var list: List<BottomCarousalModel> = searchList

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomCarousalViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.dashboard_recyclerview_3, parent, false)

        return BottomCarousalViewHolder(v)
    }

    override fun onBindViewHolder(holder: BottomCarousalViewHolder, position: Int) {

        val item = list[position]

        holder.image1.setImageResource(list[position].getImage())
        holder.text1.text = list[position].getTitle()
        holder.text2.text = list[position].getSubTitle()

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item )
            }
        }
        holder.learnBtn.setOnClickListener {
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
        fun onClick(position: Int, model: BottomCarousalModel)
    }


    override fun getItemCount(): Int {

        return list.size

    }
    class BottomCarousalViewHolder(view: View): RecyclerView.ViewHolder(view){

        val image1: ImageView = itemView.findViewById(R.id.imageView)
        val text1 : TextView = itemView.findViewById(R.id.card_text1)
        val text2 : TextView = itemView.findViewById(R.id.card_text2)
        val learnBtn : Button = itemView.findViewById(R.id.btn1)

    }
}