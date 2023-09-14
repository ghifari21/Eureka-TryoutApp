package com.gosty.tryoutapp.utils

object Utility {
    /***
     * This method to get random string to make ID.
     * @author Ghifari Octaverin
     * @since Sept 4th, 2023
     */
    fun getRandomString() : String {
        val allowedChars = '0'..'9'
        return (1..8)
            .map { allowedChars.random() }
            .joinToString("")
    }
}