package com.khatch.projektarbete_vg.apiGoogleBooks

import com.google.gson.annotations.SerializedName

data class ImageLinks(
    @SerializedName("smallThumbnail" ) var smallThumbnail : String? = null,
    @SerializedName("thumbnail"      ) var thumbnail      : String? = null
) {
    override fun toString(): String {
        return "ImageLinks(smallThumbnail=$smallThumbnail, thumbnail=$thumbnail)"
    }
}
