package com.khatch.projektarbete_vg.apiGoogleBooks

import com.google.gson.annotations.SerializedName

data class GoogleBooksResponse(
    @SerializedName("items") val items: List<GoogleBookItem> = emptyList()
) {}