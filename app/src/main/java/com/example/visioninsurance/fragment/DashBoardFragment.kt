package com.example.visioninsurance.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.visioninsurance.R
import com.example.visioninsurance.activity.DashBoard
import com.example.visioninsurance.adaptors.BottomCarousalAdapter
import com.example.visioninsurance.adaptors.CarousalRecyclerAdapter
import com.example.visioninsurance.adaptors.MiddleCarousalAdapter
import com.example.visioninsurance.model.BottomCarousalModel
import com.example.visioninsurance.model.MiddleCarousalModel
import com.example.visioninsurance.util.createNotification
import io.mob.resu.reandroidsdk.ReAndroidSDK
import org.json.JSONObject

lateinit var parentActivity: DashBoard


open class DashBoardFragment : Fragment() {

    lateinit var middleCarousalView: RecyclerView
    lateinit var topCarousalView: RecyclerView
    lateinit var bottomCarousalView:RecyclerView
    lateinit var carousalRecyclerAdapter: CarousalRecyclerAdapter
    lateinit var middleCarousalAdapter: MiddleCarousalAdapter
    lateinit var bottomCarousalAdapter: BottomCarousalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initializeActivity()

        val view = inflater.inflate(R.layout.fragment_dash_board, container, false)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initializeViews(view)
    }

    private fun initializeViews(view: View) {
        topCarousalView = view.findViewById<RecyclerView>(R.id.recyclerView1)
        topCarousalView.setHasFixedSize(true)
        topCarousalView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        val imageList: MutableList<Int> = ArrayList()
        imageList.add(R.drawable.airplane)
        imageList.add(R.drawable.auto)
        imageList.add(R.drawable.life)
        carousalRecyclerAdapter = CarousalRecyclerAdapter(imageList)
        topCarousalView.adapter = carousalRecyclerAdapter


        middleCarousalView = view.findViewById(R.id.recyclerView2)
        middleCarousalView.setHasFixedSize(true)
        middleCarousalView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        val servicesList : MutableList<MiddleCarousalModel> = ArrayList()
        servicesList.add(MiddleCarousalModel(R.drawable.car_insurance4,"Auto Insurance","14 plans"))
        servicesList.add(MiddleCarousalModel(R.drawable.family1,"Life Insurance", "16 plans"))
        servicesList.add(MiddleCarousalModel(R.drawable.life_protection1, "Health Insurance", "14 plans"))
        servicesList.add(MiddleCarousalModel(R.drawable.travel_insurance1,"Travel Insurance","15 plans"))
        servicesList.add(MiddleCarousalModel(R.drawable.accident1,"Accidental Insurance", "20 plans"))
        servicesList.add(MiddleCarousalModel(R.drawable.retirement1,"Retirement Plans","17 plans"))
        middleCarousalAdapter = MiddleCarousalAdapter(servicesList)
        middleCarousalView.adapter = middleCarousalAdapter

        bottomCarousalView = view.findViewById(R.id.recyclerView3)
        bottomCarousalView.setHasFixedSize(true)
        bottomCarousalView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        val bottomList : MutableList<BottomCarousalModel> = ArrayList()
        bottomList.add(BottomCarousalModel(R.drawable.car_insurance4,"Auto Insurance","14 plans"))
        bottomList.add(BottomCarousalModel(R.drawable.family1,"Life Insurance", "16 plans"))
        bottomList.add(BottomCarousalModel(R.drawable.life_protection1, "Health Insurance", "14 plans"))
        bottomList.add(BottomCarousalModel(R.drawable.travel_insurance1,"Travel Insurance","15 plans"))
        bottomList.add(BottomCarousalModel(R.drawable.accident1,"Accidental Insurance", "20 plans"))
        bottomList.add(BottomCarousalModel(R.drawable.retirement1,"Retirement Plans","17 plans"))
        bottomCarousalAdapter = BottomCarousalAdapter(bottomList)
        bottomCarousalView.adapter = bottomCarousalAdapter
    }

    private fun locationUpdate() {

        ReAndroidSDK.getInstance(activity).deleteNotificationByCampaignId("k2S||FaV|vV|T_tX7N6|1003123|Bulk|20221115060532")
    }

    private fun conversionTracking() {

        val jsonObject = JSONObject()
        jsonObject.put("appId", "123")
        jsonObject.put("pName", "earbuds")
        ReAndroidSDK.getInstance(activity).appConversionTracking(jsonObject)
        ReAndroidSDK.getInstance(activity).appConversionTracking()

    }

    open fun initializeActivity() {
        parentActivity = activity as DashBoard

    }

    private fun showNotification() {
        val title = "Welcome,"
        val body = "get more updates about Auto Insurance from Vision Insurance"
        activity?.let { createNotification(it,title,body) }

        ReAndroidSDK.getInstance(activity).addNewNotification(title,body,"com.example.visioninsurance.activity.DashBoard")
        parentActivity.loadFragment(AutoInsuranceFragment())

    }


}