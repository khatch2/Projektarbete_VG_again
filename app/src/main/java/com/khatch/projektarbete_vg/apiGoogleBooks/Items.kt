package com.khatch.projektarbete_vg.apiGoogleBooks

import com.google.gson.annotations.SerializedName

data class Items (
    @SerializedName("kind")  val inside_kind: String? = "",
    @SerializedName("id")  val inside_id: String? = "",
    @SerializedName("etag")  val inside_etag: String? = "",
    @SerializedName("selfLink")  val inside_selfLink: String? = null,
    @SerializedName("volumeInfo")  val inside_volumeInfo: VolumeInfo? = VolumeInfo(),
    @SerializedName("saleInfo"   ) var saleInfo   : SaleInfo?   = SaleInfo(),
    @SerializedName("accessInfo" ) var accessInfo : AccessInfo? = AccessInfo()
) {
    override fun toString(): String {
        return "Items(inside_kind=$inside_kind, inside_id=$inside_id, inside_etag=$inside_etag, inside_selfLink=$inside_selfLink, inside_volumeInfo=$inside_volumeInfo, saleInfo=$saleInfo, accessInfo=$accessInfo)"
    }

}
