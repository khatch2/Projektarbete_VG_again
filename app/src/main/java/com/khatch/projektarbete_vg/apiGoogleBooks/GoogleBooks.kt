package com.khatch.projektarbete_vg.apiGoogleBooks

import com.google.gson.annotations.SerializedName

data class GoogleBooks(
    /*
    var internalQuery: String = "karl"
    ) {
    // Template from API Object
    */

    @SerializedName("kind") val myKind : String? = null,
    @SerializedName("totalItems") val myTotalItems : Int? = 0,
    @SerializedName("items") val myItems : ArrayList<Items> = arrayListOf()
) {
    override fun toString(): String {
        return "GoogleBooks(myKind=$myKind, myTotalItems=$myTotalItems, myItems=$myItems)"
    }

}
