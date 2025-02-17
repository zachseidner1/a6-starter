package com.example.a6starter.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface ViewModelEffect

abstract class BaseViewModel<UiState, Effect : ViewModelEffect>(initialUiState: UiState) :
    ViewModel() {

    private val _uiStateFlow = MutableStateFlow(initialUiState)
    val uiStateFlow: StateFlow<UiState> = _uiStateFlow.asStateFlow()

    private val _effectFlow = Channel<Effect>()
    val effectFlow = _effectFlow.receiveAsFlow()

    @Composable
    fun collectUiStateValue(): UiState = uiStateFlow.collectAsState().value

    /**
     * Applies a mutation to the current [UiState] and emits the new state.
     *
     * @param mutation A function that operates on the current [UiState] and returns a new [UiState].
     *
     * Most often, you'll want to `copy` the current state, changing just one of its properties,
     * and then emit the new state.
     */
    protected fun applyMutation(mutation: UiState.() -> UiState) {
        _uiStateFlow.value = _uiStateFlow.value.mutation()
    }

    /**
     * Sends an effect to the effect flow asynchronously in ViewModelScope
     */
    protected fun effectAsync(effect: Effect) {
        viewModelScope.launch {
            _effectFlow.send(effect)
        }
    }

    /**
     * Sends an effect to the effect flow, and blocks until the effect is received.
     * Can be helpful to guarantee that the effect was received
     */
    protected suspend fun effect(effect: Effect) {
        _effectFlow.send(effect)
    }

    /**
     * Asynchronously starts collecting the given flow.
     */
    fun <T> asyncCollect(flow: StateFlow<T>, collector: (T) -> Unit): Job {
        return viewModelScope.launch {
            flow.collect { collector(it) }
        }
    }
}
