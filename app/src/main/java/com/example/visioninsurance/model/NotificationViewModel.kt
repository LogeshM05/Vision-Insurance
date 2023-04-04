package com.example.visioninsurance.model

class NotificationViewModel {
    private var body = ""
    private var titleColor = ""
    private var contentBgColor = ""
    private var bodyColor = ""

    fun getTitleColor(): String? {
        return titleColor
    }

    fun setTitleColor(titleColor: String) {
        this.titleColor = titleColor
    }

    fun getContentBgColor(): String? {
        return contentBgColor
    }

    fun setContentBgColor(contentBgColor: String) {
        this.contentBgColor = contentBgColor
    }

    fun getBodyColor(): String? {
        return bodyColor
    }

    fun setBodyColor(bodyColor: String) {
        this.bodyColor = bodyColor
    }

    private var title = ""
    private var subTitle = ""
    private var notificationImageUrl = ""
    private var activityName = ""
    private var fragmentName = ""
    private var campaignId = ""
    private var customParams = "{}"
    private var notificationId = ""
    private var MobileFriendlyUrl = ""
    private var customActions = "[]"

    fun getCarousel(): String? {
        return carousel
    }

    fun setCarousel(carousel: String) {
        this.carousel = carousel
    }

    private var carousel = "[]"
    private var pushType = ""
    private var bannerStyle = ""
    private var sourceType = ""
    private var channelName = ""
    private var channelID = ""

    fun getTimeStamp(): String? {
        return timeStamp
    }

    fun setTimeStamp(timeStamp: String) {
        this.timeStamp = timeStamp
    }

    private var timeStamp = ""

    fun getIsCarousel(): String? {
        return isCarousel
    }

    fun setIsCarousel(isCarousel: String) {
        this.isCarousel = isCarousel
    }

    private var isCarousel = "false"
    private var ttl = ""
    private var url = ""
    private var tag = ""

    fun getTag(): String? {
        return tag
    }

    fun setTag(tag: String) {
        this.tag = tag
    }

    fun getChannelName(): String? {
        return channelName
    }

    fun setChannelName(channelName: String) {
        this.channelName = channelName
    }

    fun getChannelID(): String? {
        return channelID
    }

    fun setChannelID(channelID: String) {
        this.channelID = channelID
    }

    private var isRead = false

    fun isRead(): Boolean {
        return isRead
    }

    fun setRead(read: Boolean) {
        isRead = read
    }

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String) {
        this.url = url
    }

    fun getCustomActions(): String? {
        return customActions
    }

    fun setCustomActions(customActions: String) {
        this.customActions = customActions
    }

    fun getPushType(): String? {
        return pushType
    }

    fun setPushType(pushType: String) {
        this.pushType = pushType
    }

    fun getBannerStyle(): String? {
        return bannerStyle
    }

    fun setBannerStyle(bannerStyle: String) {
        this.bannerStyle = bannerStyle
    }

    fun getSourceType(): String? {
        return sourceType
    }

    fun setSourceType(sourceType: String) {
        this.sourceType = sourceType
    }

    fun getTtl(): String? {
        return ttl
    }

    fun setTtl(ttl: String) {
        this.ttl = ttl
    }


    fun getSubTitle(): String? {
        return subTitle
    }

    fun setSubTitle(subTitle: String) {
        this.subTitle = subTitle
    }


    fun getMobileFriendlyUrl(): String? {
        return MobileFriendlyUrl
    }

    fun setMobileFriendlyUrl(mobileFriendlyUrl: String) {
        MobileFriendlyUrl = mobileFriendlyUrl
    }

    fun getCustomParams(): String? {
        return customParams
    }

    fun setCustomParams(customParams: String) {
        this.customParams = customParams
    }

    fun getBody(): String? {
        return body
    }

    fun setBody(body: String) {
        this.body = body
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getNotificationImageUrl(): String? {
        return notificationImageUrl
    }

    fun setNotificationImageUrl(notificationImageUrl: String) {
        this.notificationImageUrl = notificationImageUrl
    }

    fun getActivityName(): String? {
        return activityName
    }

    fun setActivityName(activityName: String) {
        this.activityName = activityName
    }

    fun getFragmentName(): String? {
        return fragmentName
    }

    fun setFragmentName(fragmentName: String) {
        this.fragmentName = fragmentName
    }

    fun getCampaignId(): String? {
        return campaignId
    }

    fun setCampaignId(campaignId: String) {
        this.campaignId = campaignId
    }

    fun getNotificationId(): String? {
        return notificationId
    }

    fun setNotificationId(notificationId: String) {
        this.notificationId = notificationId
    }

}