package com.example.a6starter.ui.screens.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun OtherScreen() = CenteredScreen {
    Text("Hello world!", fontSize = 36.sp, color = Color(0xFF2E6F40))
}