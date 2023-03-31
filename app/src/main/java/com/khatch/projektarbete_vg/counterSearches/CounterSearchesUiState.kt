package com.khatch.projektarbete_vg.counterSearches

data class CounterSearchesUiState(
    var counterSearchesValue: Int = 0,
    var searchQueries: Array<String> = arrayOf<String>()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CounterSearchesUiState

        if (counterSearchesValue != other.counterSearchesValue) return false
        if (!searchQueries.contentEquals(other.searchQueries)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = counterSearchesValue
        result = 31 * result + searchQueries.contentHashCode()
        return result
    }
}