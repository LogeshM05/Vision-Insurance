package com.example.visioninsurance.model

class BottomCarousalModel(serviceImage : Int, serviceText: String, serviceSubText:String ) {

    private var image = serviceImage
    private var title   = serviceText
    private var subTitle  = serviceSubText


    fun getImage(): Int {
        return image
    }

    fun getTitle(): String {
        return title
    }

    fun getSubTitle(): String{
        return subTitle
    }
}
