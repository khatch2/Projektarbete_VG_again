package com.khatch.projektarbete_vg.counter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CounterViewModel : ViewModel() {
    private val _state = MutableStateFlow(CounterUiState())
    val uiState : StateFlow<CounterUiState> = _state.asStateFlow()
    fun add() {
        _state.update { index -> index.copy(counterValue = index.counterValue + 1) }
    }
    fun minus() {
        _state.update { index -> index.copy(counterValue = index.counterValue - 1) }
    }
}
