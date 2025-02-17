package com.example.a6starter.ui.screens.main

import androidx.lifecycle.viewModelScope
import com.example.a6starter.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainScreenViewState(
    val isButtonLoading: Boolean = false,
    val shouldNavigate: Boolean = false,
)


@HiltViewModel
class MainScreenViewModel @Inject constructor(
) : BaseViewModel<MainScreenViewState>(MainScreenViewState()) {

    fun signIn() = viewModelScope.launch {
        applyMutation { copy(isButtonLoading = true) }
        delay(3000L)
        applyMutation { copy(isButtonLoading = false, shouldNavigate = Math.random() < .5) }
    }
}