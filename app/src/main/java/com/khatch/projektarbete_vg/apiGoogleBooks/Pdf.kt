package com.khatch.projektarbete_vg.apiGoogleBooks

import com.google.gson.annotations.SerializedName

data class Pdf(
    @SerializedName("isAvailable"  ) var isAvailable  : Boolean? = null,
    @SerializedName("downloadLink" ) var downloadLink : String?  = null
) {
    override fun toString(): String {
        return "Pdf(isAvailable=$isAvailable, downloadLink=$downloadLink)"
    }
}
