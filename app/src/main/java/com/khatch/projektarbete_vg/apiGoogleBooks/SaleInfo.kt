package com.khatch.projektarbete_vg.apiGoogleBooks

import com.google.gson.annotations.SerializedName

class SaleInfo(
    @SerializedName("country"     ) var country     : String?  = null,
    @SerializedName("saleability" ) var saleability : String?  = null,
    @SerializedName("isEbook"     ) var isEbook     : Boolean? = null,
    @SerializedName("buyLink"     ) var buyLink     : String?  = null
) {
    override fun toString(): String {
        return "SaleInfo(country=$country, saleability=$saleability, isEbook=$isEbook, buyLink=$buyLink)"
    }

}
