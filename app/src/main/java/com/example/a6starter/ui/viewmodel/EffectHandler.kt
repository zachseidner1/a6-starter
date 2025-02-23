package com.example.a6starter.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun <Effect : ViewModelEffect> EffectHandler(
    effectFlow: Flow<Effect>,
    effectHandler: (effect: Effect) -> Unit
) {
    LaunchedEffect(Unit) {
        effectFlow.collect {
            effectHandler(it)
        }
    }
}