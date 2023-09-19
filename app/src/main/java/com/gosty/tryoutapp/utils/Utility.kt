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

    /***
     * This method to resize image in html format.
     * @author Ghifari Octaverin
     * @since Sept 18th, 2023
     */
    fun String.resizeImageHtml(): String {
        return this.replace(
            "<img\\s+src=\"(.*?)\"(\\s*/)?\\s*>".toRegex(),
            "<img src=\"$1\" style=\"max-width: 100%; max-height: 100%; width: auto; height: auto;\">"
        )
    }
}