package com.khatch.projektarbete_vg.counterSearches

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CounterSearchesViewModel : ViewModel() {
    private val _state = MutableStateFlow(CounterSearchesUiState())
    val uiState : StateFlow<CounterSearchesUiState> = _state.asStateFlow()
    fun add() {
        _state.update { index -> index.copy(counterSearchesValue = index.counterSearchesValue + 1) }
    }
    fun minus() {
        _state.update { index -> index.copy(counterSearchesValue = index.counterSearchesValue - 1) }
    }
    fun push(element:String) {
        _state.update { index -> index.copy(searchQueries = index.searchQueries.plus(element))
        }
    }
    /*
    fun pop() {
        _state.update { index -> index.copy(searchQueries =
        index.searchQueries.copyOf(index.searchQueries.lastIndex) )}
    }
    */
}
