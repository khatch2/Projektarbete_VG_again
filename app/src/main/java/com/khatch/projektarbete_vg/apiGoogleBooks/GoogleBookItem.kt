package com.khatch.projektarbete_vg.apiGoogleBooks

import com.google.gson.annotations.SerializedName

data class GoogleBookItem(
    @SerializedName("volumeInfo") val volumeInfo: GoogleBookInfo
) {
    override fun toString(): String {
        return "GoogleBookItem(volumeInfo=$volumeInfo)"
    }
}
