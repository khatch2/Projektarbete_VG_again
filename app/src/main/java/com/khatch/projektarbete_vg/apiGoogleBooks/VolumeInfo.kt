package com.khatch.projektarbete_vg.apiGoogleBooks

import com.google.gson.annotations.SerializedName

class VolumeInfo {
    @SerializedName("title")  val inside_inside_title: String = ""
    @SerializedName("authors")  val inside_inside_authors: String = ""
    @SerializedName("publisher")  val inside_inside_publisher: String = ""
    @SerializedName("publishedDate")  val inside_inside_publishedDate : String = ""
    @SerializedName("description")  val inside_inside_description : String = ""

    override fun toString(): String {
        return "VolumeInfo(inside_inside_title='$inside_inside_title'," +
                " inside_inside_authors='$inside_inside_authors'," +
                " inside_inside_publisher='$inside_inside_publisher'," +
                " inside_inside_publishedDate='$inside_inside_publishedDate'," +
                " inside_inside_description='$inside_inside_description')"
    }
}
