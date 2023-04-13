package com.khatch.projektarbete_vg.apiGoogleBooks

import com.google.gson.annotations.SerializedName

data class GoogleBooks(
    var internalQuery: String = "karl"
    ) {
    // Template from API Object

    @SerializedName("kind") val myKind : String = ""
    @SerializedName("totalItems") val myTotalItems : Int = 0
    @SerializedName("items") val myItems : Array<Items> = emptyArray<Items>()

    override fun toString(): String {
        return "GoogleBooks(internalQuery='$internalQuery'," +
                " myKind='$myKind', myTotalItems=$myTotalItems," +
                " myItems=${myItems.contentToString()})"
    }

    /*
    @SerializedName("smallThumbnail") val myImage : String? = ""
    @SerializedName("previewLink") val myLink : String? = ""
    @SerializedName("title") val myTitle : String? = ""
    @SerializedName("authors") val myAuthors : String? = ""
    override fun toString(): String {
        return "GoogleBooks(internalQuery='$internalQuery', myKind='$myKind'," +
                " myTotalItems=$myTotalItems, myItems=${myItems.contentToString()}," +
                " myImage=$myImage, myLink=$myLink, myTitle=$myTitle, myAuthors=$myAuthors)"
    }
    */
}
