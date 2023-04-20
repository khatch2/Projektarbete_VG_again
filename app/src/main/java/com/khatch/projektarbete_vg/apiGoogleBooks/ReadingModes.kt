package com.khatch.projektarbete_vg.apiGoogleBooks

import com.google.gson.annotations.SerializedName

data class ReadingModes(
    @SerializedName("text"  ) var text  : Boolean? = null,
    @SerializedName("image" ) var image : Boolean? = null
) {
    override fun toString(): String {
        return "ReadingModes(text=$text, image=$image)"
    }
}
