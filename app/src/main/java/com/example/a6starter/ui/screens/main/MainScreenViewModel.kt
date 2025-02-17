package com.example.a6starter.ui.screens.main

import androidx.lifecycle.viewModelScope
import com.example.a6starter.ui.viewmodel.BaseViewModel
import com.example.a6starter.ui.viewmodel.ViewModelEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainScreenViewState(
    val isButtonLoading: Boolean = false
)

sealed class MainScreenViewModelEffect : ViewModelEffect {
    data object Navigate : MainScreenViewModelEffect()
    data class Error(val text: String) : MainScreenViewModelEffect()
}

@HiltViewModel
class MainScreenViewModel @Inject constructor(
) : BaseViewModel<MainScreenViewState, MainScreenViewModelEffect>(MainScreenViewState()) {

    fun signIn() = viewModelScope.launch {
        applyMutation { copy(isButtonLoading = true) }
        delay(3000L)
        if (Math.random() < .5) {
            effect(MainScreenViewModelEffect.Error("Unlucky"))
        } else {
            effect(MainScreenViewModelEffect.Navigate)
        }
        applyMutation { copy(isButtonLoading = false) }
    }
}