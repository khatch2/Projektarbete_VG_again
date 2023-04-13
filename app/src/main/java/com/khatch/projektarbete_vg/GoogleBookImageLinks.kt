package com.khatch.projektarbete_vg

import com.google.gson.annotations.SerializedName

data class GoogleBookImageLinks(
    @SerializedName("smallThumbnail") val smallThumbnail: String?,
    @SerializedName("thumbnail") val thumbnail: String?
) {}