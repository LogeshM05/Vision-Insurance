@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.example.visioninsurance.adaptors

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.visioninsurance.R
import com.example.visioninsurance.fragment.InboxFragment
import org.json.JSONArray
import org.json.JSONObject


class Adapter( private var notificationData: ArrayList<JSONObject>,private var notificationAdapterListener: NotificationAdapterListener) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    lateinit var context: Context
    lateinit var activity: Activity

    lateinit var adapter: Adapter
    lateinit var listener: NotificationAdapterListener

    private var list: ArrayList<JSONObject>

    // Constructor for initialization
    init {
        this.list = notificationData
         this.listener = notificationAdapterListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetItems(data1: ArrayList<JSONObject>) {
        if (data1 != null) {
            this.list = data1
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_card_view, parent, false)


        return ViewHolder(v)

    }

    // Binding data to the into specified position
    @SuppressLint("NotifyDataSetChanged", "LongLogTag")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.setData(notificationData[position])

        val data = list[position]
        holder.textView1.text = data.optString("title")
        holder.textView2.text = data.optString("subTitle")
        holder.textView2.text = data.optString("body")
        holder.textView3.text = data.optString("id")

        var bundle = Bundle()

        holder.deleteButton.setOnClickListener {

            listener.onClickDelete(list,position)
           // InboxFragment().onDeleteNotification(data.optString("campaignId"),position)

        }

        holder.card.setOnClickListener {
            listener.onClick(list,position)
        }
    }

    override fun getItemCount(): Int {
        // Returns number of items
        // currently available in Adapter
        return list.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var notificationData: JSONObject
        val textView3: TextView = itemView.findViewById(R.id.notification_id)
        val textView1: TextView = itemView.findViewById(R.id.title)
        val textView2: TextView = itemView.findViewById(R.id.sub_title)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val applyButton:Button = itemView.findViewById(R.id.applyButton1)
        val card:LinearLayout = itemView.findViewById(R.id.linearLayout)


    }

//    override fun onClick(v: View) {
//
//        val id: Int = v.id
//
//        try {
//          if (id == R.id.deleteButton) {
//                listener.onClickDelete(list,)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }


}

