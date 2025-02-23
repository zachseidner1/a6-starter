package com.example.a6starter.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.a6starter.ui.viewmodel.ViewModelEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun <Effect : ViewModelEffect> EffectHandler(effectFlow: Flow<Effect>, onEffect: (Effect) -> Unit) {
    LaunchedEffect(Unit) {
        effectFlow.collect {
            onEffect(it)
        }
    }
}