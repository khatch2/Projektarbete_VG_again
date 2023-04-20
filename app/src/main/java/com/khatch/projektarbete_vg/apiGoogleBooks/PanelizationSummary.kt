package com.khatch.projektarbete_vg.apiGoogleBooks

import com.google.gson.annotations.SerializedName

data class PanelizationSummary (
    @SerializedName("containsEpubBubbles"  ) var containsEpubBubbles  : Boolean? = null,
    @SerializedName("containsImageBubbles" ) var containsImageBubbles : Boolean? = null
) {
    override fun toString(): String {
        return "PanelizationSummary(containsEpubBubbles=$containsEpubBubbles, containsImageBubbles=$containsImageBubbles)"
    }
}
