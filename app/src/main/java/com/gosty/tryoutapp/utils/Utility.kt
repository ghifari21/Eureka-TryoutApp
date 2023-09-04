package com.gosty.tryoutapp.utils

object Utility {
    fun getRandomString() : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..16)
            .map { allowedChars.random() }
            .joinToString("")
    }
}