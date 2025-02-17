package com.example.a6starter.ui.screens.main

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object HomeScreen : Screen()

    @Serializable
    data object OtherScreen : Screen()
}