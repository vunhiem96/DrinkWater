package com.vunhiem.drinkwater.Model

import android.os.Parcelable


class WaterHistory {
    var id:Int=0
    var time:String?= null
    var waterUp:Int?=null

    constructor(){}

    constructor(id:Int,time:String, waterUp:Int)
    {
        this.id =id
        this.time = time
        this.waterUp=waterUp
    }
}