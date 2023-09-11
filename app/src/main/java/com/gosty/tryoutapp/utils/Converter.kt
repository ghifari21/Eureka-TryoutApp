package com.gosty.tryoutapp.utils

import android.text.format.DateUtils

object Converter {

    /*
    *   this method is to convert from milisecond to minute-second that represent how much the time users need to finish their tests
    *   @author Andi
    *   @since September 11th, 2023
    * */
    fun milisecondToMinuteAndSecond(milliseconds: Long): String {
        val totalSeconds = (milliseconds / 1000).toInt()
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return if (minutes == 0){
            String.format("%02d detik", seconds)
        } else {
            String.format("%02d menit %02d detik", minutes, seconds)
        }
    }

    /*
    *   this method is to convert raw datetime to more managable datetime that represent the date and the time users have done their test
    *   @author Andi
    *   @since September 11th, 2023
    * */
    fun dateTime(dateTime : Long) : String{
        return DateUtils.getRelativeTimeSpanString(dateTime).toString()
    }
}