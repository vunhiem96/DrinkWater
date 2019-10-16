package com.vunhiem.drinkwater.Model

class WaterWeek {
    var id:Int=0
var waterdrink:Int?= null
var waterdrinkCompletion:Int?=null
var waterDrinkCount:Int= 0


constructor(){}

constructor(id:Int,waterdrink:Int, waterdrinkCompletion:Int,waterDrinkCount:Int)
{
    this.id =id
    this.waterdrink = waterdrink
    this.waterdrinkCompletion=waterdrinkCompletion
    this.waterDrinkCount=waterDrinkCount


}
}