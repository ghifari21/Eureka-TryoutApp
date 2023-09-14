package com.gosty.tryoutapp.utils

object Utility {
    fun getRandomString() : String {
        val allowedChars = '0'..'9'
        return (1..8)
            .map { allowedChars.random() }
            .joinToString("")
    }
}