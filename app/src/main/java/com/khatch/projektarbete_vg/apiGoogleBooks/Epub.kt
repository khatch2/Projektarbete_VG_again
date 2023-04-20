package com.khatch.projektarbete_vg.apiGoogleBooks

import com.google.gson.annotations.SerializedName

data class Epub(
    @SerializedName("isAvailable"  ) var isAvailable  : Boolean? = null,
    @SerializedName("downloadLink" ) var downloadLink : String?  = null
) {
    override fun toString(): String {
        return "Epub(isAvailable=$isAvailable, downloadLink=$downloadLink)"
    }
}
