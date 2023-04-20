package com.khatch.projektarbete_vg.apiGoogleBooks

import com.google.gson.annotations.SerializedName
import com.khatch.projektarbete_vg.GoogleBookImageLinks

data class GoogleBookInfo(
    @SerializedName("title") val title: String,
    @SerializedName("authors") val authors: List<String> = emptyList(),
    @SerializedName("publisher") val publisher: String?,
    @SerializedName("publishedDate") val publishedDate: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("imageLinks") val imageLinks: GoogleBookImageLinks?
) {
    override fun toString(): String {
        return "GoogleBookInfo(title='$title', authors=$authors, publisher=$publisher, publishedDate=$publishedDate, description=$description, imageLinks=$imageLinks)"
    }
}