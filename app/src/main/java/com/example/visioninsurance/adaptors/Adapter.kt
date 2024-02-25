@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.example.visioninsurance.adaptors

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.visioninsurance.R
import org.json.JSONObject
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import org.json.JSONArray
import org.json.JSONException

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
        val imageUrl = data.optString("url")
        val customActionsString = data.optString("customActions")

        try {
            val customActionsArray = JSONArray(customActionsString)

            if (customActionsArray.length() > 0) {
                val firstAction = customActionsArray.getJSONObject(0)

                val actionName1 = firstAction.optString("actionName")

                holder.customBtn1.text = actionName1
                if (customActionsArray.length() > 1) {
                    val secondAction = customActionsArray.getJSONObject(1)
                    val actionName2 = secondAction.optString("actionName")
                    holder.customBtn2.text = actionName2
                } else {
                    holder.customBtn2.visibility = View.GONE
                }

            } else {
                // Handle the case where there are no actions in the array
                holder.customBtn1.visibility = View.GONE
                holder.customBtn2.visibility = View.GONE
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            // Handle the JSONException appropriately
        }

        if (imageUrl.isNotEmpty()) {
            // If the URL is present, set visibility of ImageView and its layout to VISIBLE
            holder.imageView.visibility = View.VISIBLE
            Glide.with(holder.itemView)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView)
        } else {
            // If the URL is not present, set visibility of ImageView and its layout to GONE
            holder.imageView.visibility = View.GONE
            holder.imageView.layoutParams.height = 0 // Set height to 0 to remove space
        }

        var bundle = Bundle()

        holder.customBtn1.setOnClickListener {
            val intent = Intent()
            listener.onCustomAction1CLick(list,position,intent)
           // InboxFragment().onDeleteNotification(data.optString("campaignId"),position)

        }
        holder.customBtn2.setOnClickListener {
            val intent = Intent()
            listener.onCustomAction2CLick(list,position,intent)

//            listener.onClickDelete(list,position)
            // InboxFragment().onDeleteNotification(data.optString("campaignId"),position)

        }

        holder.imageView.setOnClickListener {
            listener.onClick(list,position)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // Do nothing, as we don't support moving items
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Notify your listener about the swipe event
                    listener.onClickDelete(list,viewHolder.layoutPosition)
            }
        }

        // Attach the ItemTouchHelper to the RecyclerView
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun getItemCount(): Int {
        // Returns number of items
        // currently available in Adapter
        return list.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var notificationData: JSONObject
        val imageView: ImageView = itemView.findViewById(R.id.notificationImage)
        val textView1: TextView = itemView.findViewById(R.id.title)
        val textView2: TextView = itemView.findViewById(R.id.sub_title)
//        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val customBtn1:Button = itemView.findViewById(R.id.cusBtn1)
        val customBtn2:Button = itemView.findViewById(R.id.cusBtn2)



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

