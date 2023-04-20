package com.khatch.projektarbete_vg.apiGoogleBooks

import com.google.gson.annotations.SerializedName

data class GoogleBookImageLinks (
    @SerializedName("smallThumbnail") val smallThumbnail: String?,
    @SerializedName("thumbnail") val thumbnail: String?
) {
    override fun toString(): String {
        return "GoogleBookImageLinks(smallThumbnail=$smallThumbnail, thumbnail=$thumbnail)"
    }
}