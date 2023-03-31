package com.khatch.projektarbete_vg.counterSearches

data class CounterSearchesUiState(
    var counterSearchesValue: Int = 0,
    var searchQueries: ArrayList<String> = arrayListOf<String>()
) {

}