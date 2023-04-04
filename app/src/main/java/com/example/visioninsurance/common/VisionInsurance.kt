package com.example.visioninsurance.common

import android.app.Application
import io.mob.resu.reandroidsdk.AppConstants
import io.mob.resu.reandroidsdk.ReAndroidSDK

class VisionInsurance : Application() {
//    private var mAppController: VisionInsurance? = null
//
//
//    fun getInstance(): VisionInsurance? {
//        return mAppController
//    }
    override fun onCreate() {
        super.onCreate()

        ReAndroidSDK.getInstance(this)
        AppConstants.LogFlag = true

    }
}