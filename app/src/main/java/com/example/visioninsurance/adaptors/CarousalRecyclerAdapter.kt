package com.example.visioninsurance.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.visioninsurance.R

class CarousalRecyclerAdapter(private var imageList: List<Int>) :
    RecyclerView.Adapter<CarousalRecyclerAdapter.CarousalViewHolder>() {


    private var imageList1: List<Int> = imageList
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarousalViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.dashboard_recyclerview_1, parent, false)

        return CarousalViewHolder(v)


    }


    override fun getItemCount(): Int {
        return imageList1.size
    }


    override fun onBindViewHolder(holder: CarousalViewHolder, position: Int) {
        holder.image1.setImageResource(imageList1.get(position))
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, imageList)

            }
        }
        holder.readButton.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, imageList)
            }
        }

    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, list: List<Int>)
    }

    class CarousalViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image1: ImageView = itemView.findViewById(R.id.imageView)
        val readButton: Button = itemView.findViewById(R.id.card_button)

    }

}