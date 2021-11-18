package com.example.myapplication

//class AnnouncementData {
//    /**set Data*/
//    var announcement_id: String ? = null
//    var title: String ? = null
//    var desc: String ? = null
//    var thumbnail: String ? = null
//
//    constructor() {}
//
//    constructor(
//        announcement_id: String,
//        title: String,
//        desc: String,
//        thumbnail: String,
//    ) {
//        this.announcement_id = announcement_id
//        this.title = title
//        this.desc = desc
//        this.thumbnail = thumbnail
//    }
//}


data class AnnouncementData(
    val announcement_id: String,
    val title: String,
    val desc: String,
    val thumbnail: String,
)