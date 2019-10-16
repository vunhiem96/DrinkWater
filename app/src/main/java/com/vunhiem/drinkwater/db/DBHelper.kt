package com.vunhiem.drinkwater.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.vunhiem.drinkwater.Model.WaterMonth
import com.vunhiem.drinkwater.Model.WaterWeek
import com.vunhiem.drinkwater.Model.Weight


class DBHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null , DATABASE_VER) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY =
            ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY,$COL_WATERDRINK INTEGER,$COL_WATERDRINK_COMPLETION FLOAT,$COL_WATERDRINK_COUNT INTEGER)")
        val CREATE_TABLE_QUERY2 =
            ("CREATE TABLE $TABLE_NAME2 ($COL_ID2 INTEGER PRIMARY KEY,$COL_WATERDRINK2 INTEGER,$COL_WATERDRINK_COMPLETION2 FLOAT,$COL_WATERDRINK_COUNT2 INTEGER)")
        val CREATE_TABLE_QUERY3 =
            ("CREATE TABLE $TABLE_NAME3 ($COL_ID3 INTEGER PRIMARY KEY,$COL_WEIGHT_WEEK FLOAT)")
        val CREATE_TABLE_QUERY4 =
            ("CREATE TABLE $TABLE_NAME4 ($COL_ID4 INTEGER PRIMARY KEY,$COL_WEIGHT_MONTH FLOAT)")
        val CREATE_TABLE_QUERY5 =
            ("CREATE TABLE $TABLE_NAME5 ($COL_ID4 INTEGER PRIMARY KEY,$COL_WEIGHT_MONTH FLOAT)")
        db!!.execSQL(CREATE_TABLE_QUERY)
        db!!.execSQL(CREATE_TABLE_QUERY2)
        db!!.execSQL(CREATE_TABLE_QUERY3)
        db!!.execSQL(CREATE_TABLE_QUERY4)
        db!!.execSQL(CREATE_TABLE_QUERY5)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME2")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME3")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME4")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME5")
        onCreate(db)
    }

    fun createDefaultNotesIfNeed() {
        val count = this.getNotesCount()
        if (count == 0) {
            val mon = WaterWeek(2, 0, 0, 0)
            val tue = WaterWeek(3, 0, 0, 0)
            val wed = WaterWeek(4, 0, 0, 0)
            val thu = WaterWeek(5, 0, 0, 0)
            val fri = WaterWeek(6, 0, 0, 0)
            val sar = WaterWeek(7, 0, 0, 0)
            val sun = WaterWeek(8, 0, 0, 0)

            this.addWaterWeek(mon)
            this.addWaterWeek(tue)
            this.addWaterWeek(wed)
            this.addWaterWeek(thu)
            this.addWaterWeek(fri)
            this.addWaterWeek(sar)
            this.addWaterWeek(sun)

        }
    }


    fun createDefaultNotesIfNeed2() {
        val count = this.getNotesCount2()
        if (count == 0) {
            for (i in 1..31) {

                this.addWaterMonth(WaterMonth(i, 0, 0, 0))
            }

        }
    }

    fun getNotesCount(): Int {
        val countQuery = "SELECT  * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)

        val count = cursor.count

        cursor.close()

        return count
    }

    fun getAllWaterMonth(): Int {
        var x = 0
        val db = this.readableDatabase
        val cur = db.rawQuery("SELECT SUM($COL_WATERDRINK2) FROM $TABLE_NAME2", null)
        if (cur.moveToFirst()) {
            x = cur.getInt(0)
        }
        return x
    }
    fun getAllCountMonth(): Int {
        var x = 0
        val db = this.readableDatabase
        val cur = db.rawQuery("SELECT SUM($COL_WATERDRINK_COUNT2) FROM $TABLE_NAME2", null)
        if (cur.moveToFirst()) {
            x = cur.getInt(0)
        }
        return x
    }

    fun getNotesCount2(): Int {
        val countQuery = "SELECT  * FROM $TABLE_NAME2"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)

        val count = cursor.count

        cursor.close()

        return count
    }
    fun getNotesCountWeightWeek(): Int {
        val countQuery = "SELECT  * FROM $TABLE_NAME3"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)

        val count = cursor.count

        cursor.close()

        return count
    }
fun getWaterDay(id: Int): WaterWeek {
    val db = this.readableDatabase
    val cursor =
        db.query(TABLE_NAME, null, COL_ID + " = ?", arrayOf(id.toString()), null, null, null)
    cursor?.moveToFirst()
    return WaterWeek(
        cursor!!.getInt(0),
        cursor.getInt(1),
        cursor.getInt(2),
        cursor.getInt(3)
    )
}
    fun getWaterMonth(id: Int): WaterMonth {
        val db = this.readableDatabase
        val cursor =
            db.query(TABLE_NAME2, null, COL_ID2 + " = ?", arrayOf(id.toString()), null, null, null)
        cursor?.moveToFirst()
        return WaterMonth(
            cursor!!.getInt(0),
            cursor.getInt(1),
            cursor.getInt(2),
            cursor.getInt(3)

        )
    }


    fun addWaterWeek(waterWeek: WaterWeek){
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, waterWeek.id)
        values.put(COL_WATERDRINK, waterWeek.waterdrink)
        values.put(COL_WATERDRINK_COMPLETION, waterWeek.waterDrinkCount)
        values.put(COL_WATERDRINK_COUNT, waterWeek.waterDrinkCount)


        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun addWaterMonth(waterMonth: WaterMonth){
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, waterMonth.id)
        values.put(COL_WATERDRINK, waterMonth.waterdrink)
        values.put(COL_WATERDRINK_COMPLETION, waterMonth.waterDrinkCount)
        values.put(COL_WATERDRINK_COUNT, waterMonth.waterDrinkCount)


        db.insert(TABLE_NAME2, null, values)
        db.close()
    }


    fun updateWaterWeek(waterWeek: WaterWeek):Int {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, waterWeek.id)
        values.put(COL_WATERDRINK, waterWeek.waterdrink)
        values.put(COL_WATERDRINK_COMPLETION, waterWeek.waterdrinkCompletion)
        values.put(COL_WATERDRINK_COUNT, waterWeek.waterDrinkCount)

        return  db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf(waterWeek.id.toString()))
    }
    fun updateWaterMonth(waterMonth: WaterMonth):Int {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, waterMonth.id)
        values.put(COL_WATERDRINK, waterMonth.waterdrink)
        values.put(COL_WATERDRINK_COMPLETION, waterMonth.waterdrinkCompletion)
        values.put(COL_WATERDRINK_COUNT, waterMonth.waterDrinkCount)

        return  db.update(TABLE_NAME2, values, "$COL_ID2=?", arrayOf(waterMonth.id.toString()))
    }



    fun updateWeightWeek(weight: Weight):Int {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID3, weight.id)
        values.put(COL_WEIGHT_WEEK, weight.weight)

        return  db.update(TABLE_NAME3, values, "$COL_ID3=?", arrayOf(weight.id.toString()))
    }
    fun addWeightWeek(weight: Weight){
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID3, weight.id)
        values.put(COL_WEIGHT_WEEK, weight.weight)
        db.insert(TABLE_NAME3, null, values)
        db.close()
    }
    fun getWeightDay(id: Int): Weight {
        val db = this.readableDatabase
        val cursor =
            db.query(TABLE_NAME3, null, COL_ID3 + " = ?", arrayOf(id.toString()), null, null, null)
        cursor?.moveToFirst()
        return Weight(
            cursor!!.getInt(0),
            cursor.getFloat(1)
        )
    }
    fun createDefaultWeightWeek() {
        val count = this.getNotesCountWeightWeek()
        if (count == 0) {
            for (i in 2..8) {

                this.addWeightWeek(Weight(i, 0f))
            }

        }
    }
    fun updateWeightMonth(weight: Weight):Int {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID4, weight.id)
        values.put(COL_WEIGHT_MONTH, weight.weight)

        return  db.update(TABLE_NAME4, values, "$COL_ID4=?", arrayOf(weight.id.toString()))
    }
    fun addWeightMonth(weight: Weight){
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID4, weight.id)
        values.put(COL_WEIGHT_MONTH, weight.weight)
        db.insert(TABLE_NAME4, null, values)
        db.close()
    }
    fun getWeightMonth(id: Int): Weight {
        val db = this.readableDatabase
        val cursor =
            db.query(TABLE_NAME4, null, COL_ID4 + " = ?", arrayOf(id.toString()), null, null, null)
        cursor?.moveToFirst()
        return Weight(
            cursor!!.getInt(0),
            cursor.getFloat(1)
        )
    }
    fun createDefaultWeightMonth() {
        val count = this.getNotesCountWeightMonth()
        if (count == 0) {
            for (i in 1..31) {

                this.addWeightMonth(Weight(i, 0f))
            }

        }
    }
    fun getNotesCountWeightMonth(): Int {
        val countQuery = "SELECT  * FROM $TABLE_NAME4"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)

        val count = cursor.count

        cursor.close()

        return count
    }
    fun updateWeightYear(weight: Weight):Int {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID4, weight.id)
        values.put(COL_WEIGHT_MONTH, weight.weight)

        return  db.update(TABLE_NAME5, values, "$COL_ID=?", arrayOf(weight.id.toString()))
    }
    fun addWeightYear(weight: Weight){
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID4, weight.id)
        values.put(COL_WEIGHT_MONTH, weight.weight)
        db.insert(TABLE_NAME5, null, values)
        db.close()
    }
    fun getWeightYear(id: Int): Weight {
        val db = this.readableDatabase
        val cursor =
            db.query(TABLE_NAME5, null, COL_ID4 + " = ?", arrayOf(id.toString()), null, null, null)
        cursor?.moveToFirst()
        return Weight(
            cursor!!.getInt(0),
            cursor.getFloat(1)
        )
    }
    fun createDefaultWeightYear() {
        val count = this.getNotesCountWeightYear()
        if (count == 0) {
            for (i in 1..12) {

                this.addWeightYear(Weight(i, 0f))
            }

        }
    }
    fun getNotesCountWeightYear(): Int {
        val countQuery = "SELECT  * FROM $TABLE_NAME5"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)

        val count = cursor.count

        cursor.close()

        return count
    }
    companion object{
        private val DATABASE_VER =1
        private val DATABASE_NAME = "Water.db"
        private val TABLE_NAME="WaterWeek"
        private val TABLE_NAME2="WaterMonth"
        private val TABLE_NAME3="Weight"
        private val TABLE_NAME4="WeightMonth"
        private val TABLE_NAME5="WeightYear"
        private val COL_ID = "Id"
        private val COL_ID2 = "Id"
        private val COL_ID3 = "Id"
        private val COL_ID4 = "Id"
        private val COL_WATERDRINK="Waterdrink"
        private val COL_WATERDRINK2="Waterdrink"
        private val COL_WEIGHT_WEEK="Weight"
        private val COL_WEIGHT_MONTH="WeightMonth"
        private val COL_WATERDRINK_COMPLETION2 = "Waterdrinkcompletion"
        private val COL_WATERDRINK_COMPLETION = "Waterdrinkcompletion"
        private val COL_WATERDRINK_COUNT = "Waterdrinkcount"
        private val COL_WATERDRINK_COUNT2 = "Waterdrinkcount"

    }
}