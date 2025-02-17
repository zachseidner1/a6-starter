package com.example.a6starter.ui.screens.main

import androidx.lifecycle.viewModelScope
import com.example.a6starter.ui.NavigationEventRepository
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
    data class Error(val text: String) : MainScreenViewModelEffect()
}

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val navigationEventRepository: NavigationEventRepository,
) : BaseViewModel<MainScreenViewState, MainScreenViewModelEffect>(MainScreenViewState()) {

    fun signIn() = viewModelScope.launch {
        applyMutation { copy(isButtonLoading = true) }
        delay(3000L)
        if (Math.random() < .5) {
            effect(MainScreenViewModelEffect.Error("Unlucky"))
        } else {
            navigationEventRepository.sendNavigationEvent(Screen.OtherScreen)
        }
        applyMutation { copy(isButtonLoading = false) }
    }
}