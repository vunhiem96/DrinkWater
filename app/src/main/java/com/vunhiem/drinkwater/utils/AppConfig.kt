package com.vunhiem.drinkwater.utils

import android.annotation.SuppressLint
import android.content.Context

object AppConfig {
    val PREF_NAME = "Android005"
    fun setGender(genDer: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("gender", genDer)
        editor.apply()
    }

    fun getGender(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("gender", "Male")
    }

    fun setWeight(weight: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("weight", weight)
        editor.apply()
    }

    fun getWeight(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("weight", "80 kg")
    }

    fun setHeight(height: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("height", height)
        editor.apply()
    }

    fun getHeight(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("height", "170 cm")
    }

    fun setWakeUp(waveUp: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("waveup", waveUp)
        editor.apply()
    }

    fun getWakeUp(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("waveup", "08:00")
    }

    fun setBedTime(bedTime: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("bedTime", bedTime)
        editor.apply()
    }

    fun getBedTime(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("bedTime", "23:00")
    }

    fun setIntervalTime(interValTime: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("intervalTime", interValTime)
        editor.apply()
    }

    fun getIntervalTime(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("intervalTime", "60F min")
    }

    fun setGoldDrink(goldDrink: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("golddrink", goldDrink)
        editor.apply()
    }

    fun getGoldDrink(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("golddrink", "1900 ml")
    }

    fun setHistoryLogin(historyLoign: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("historyLoign", historyLoign)
        editor.apply()
    }

    fun getHistoryLogin(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("historyLoign", false)

    }

    fun setSettingIcon(settingIcon: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("settingIcon", settingIcon)
        editor.apply()
    }

    fun getSettingIcon(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("settingIcon", true)
    }

    fun setReminder(Reminder: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Reminder", Reminder)
        editor.apply()
    }

    fun getReminder(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("Reminder", true)

    }

    fun setRecord(Record: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Record", Record)
        editor.apply()
    }

    fun getRecord(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("Record", true)
    }

    fun setWaterlevel(waterLevel: Int, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("waterLevel", waterLevel)
        editor.apply()
    }

    fun getWaterLevel(context: Context): Int? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("waterLevel", 0)
    }

    fun setWaterup(Waterup: Int, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("Waterup", Waterup)
        editor.apply()
    }

    fun getWaterup(context: Context): Int? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("Waterup", 100)
    }

    fun setTimeDrink(TimeDrink: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("TimeDrink", TimeDrink)
        editor.apply()
    }

    fun getTimeDrink(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("TimeDrink", "00:00")
    }

    fun setTimeNextDrink(TimeNextDrink: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("TimeNextDrink", TimeNextDrink)
        editor.apply()
    }

    fun getTimeNextDrink(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("TimeNextDrink", "00:00")
    }

    fun setRemiderMode(mode: Int, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("mode", mode)
        editor.apply()
    }

    fun getRemiderMode(context: Context): Int? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("mode", 2)
    }

    fun setTargetWeight(target: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("target", target)
        editor.apply()
    }

    fun getTargetWeight(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("target", "")
    }

    fun getCheckBed(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("CheckBed", true)

    }

    fun setCheckBed(CheckBed: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("CheckBed", CheckBed)
        editor.apply()
    }

    fun getCheckfull(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("CheckFull", false)

    }

    fun setCheckFull(CheckFull: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("CheckFull", CheckFull)
        editor.apply()
    }

    fun getCount(context: Context): Int? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("count", 0)
    }

    fun setCount(count: Int, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("count", count)
        editor.apply()
    }

    fun getCheckDailyGold(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("CheckDailyGold", true)

    }

    fun setDailyGold(CheckDailyGold: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("CheckDailyGold", CheckDailyGold)
        editor.apply()
    }

    fun setWaterup2(Waterup2: Int, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("Waterup2", Waterup2)
        editor.apply()
    }

    fun getWaterup2(context: Context): Int? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("Waterup2", 0)
    }





}