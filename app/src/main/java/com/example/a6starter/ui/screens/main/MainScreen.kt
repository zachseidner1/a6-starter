package com.example.a6starter.ui.screens.main

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.a6starter.ui.viewmodel.EffectHandler
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navigateToOtherScreen: () -> Unit,
    viewModel: MainScreenViewModel = hiltViewModel()
) = CenteredScreen {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.collectUiStateValue()

    EffectHandler(viewModel.effectFlow) {
        when (it) {
            is MainScreenViewModelEffect.Navigate -> navigateToOtherScreen()
            is MainScreenViewModelEffect.Error -> coroutineScope.launch {
                snackbarHostState.showSnackbar(it.text)
            }
        }
    }
    SnackbarHost(hostState = snackbarHostState)

    Button(onClick = { viewModel.signIn() }) {
        Row(
            modifier = Modifier.animateContentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (uiState.isButtonLoading) {
                CircularProgressIndicator(color = Color.White)
                Spacer(Modifier.width(16.dp))
            }
            Text("Login", fontSize = 24.sp)
        }
    }
}