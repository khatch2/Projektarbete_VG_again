package com.khatch.projektarbete_vg.apiGoogleBooks

import com.google.gson.annotations.SerializedName

data class IndustryIdentifiers(
    @SerializedName("type"       ) var type       : String? = null,
    @SerializedName("identifier" ) var identifier : String? = null
) {
    override fun toString(): String {
        return "IndustryIdentifiers(type=$type, identifier=$identifier)"
    }
}
