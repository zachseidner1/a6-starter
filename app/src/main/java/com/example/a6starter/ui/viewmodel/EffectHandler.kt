package com.example.a6starter.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun <Effect : ViewModelEffect> EffectHandler(
    effectFlow: Flow<Effect>,
    effectHandler: (effect: Effect) -> Unit
) {
    LaunchedEffect(Unit) {
        effectFlow.onEach {
            effectHandler(it)
        }.launchIn(this)
    }
}