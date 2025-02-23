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

    private val _effectsChannel = Channel<Effect>()
    val effectFlow = _effectsChannel.receiveAsFlow()

    @Composable
    fun collectUiStateValue(): UiState = uiStateFlow.collectAsState().value


    suspend fun sendEffect(effect: Effect) {
        _effectsChannel.send(effect)
    }

    fun sendEffectAsync(effect: Effect) = viewModelScope.launch {
        _effectsChannel.send(effect)
    }

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
     * Asynchronously starts collecting the given flow.
     */
    fun <T> asyncCollect(flow: StateFlow<T>, collector: (T) -> Unit): Job {
        return viewModelScope.launch {
            flow.collect { collector(it) }
        }
    }
}
