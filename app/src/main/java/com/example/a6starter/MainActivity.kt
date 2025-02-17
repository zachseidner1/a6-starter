package com.example.a6starter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a6starter.ui.screens.main.MainScreen
import com.example.a6starter.ui.screens.main.OtherScreen
import com.example.a6starter.ui.screens.main.Screen
import com.example.a6starter.ui.theme.A6StarterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            A6StarterTheme {
                NavHost(navController, Screen.HomeScreen) {
                    composable<Screen.HomeScreen> {
                        MainScreen({ navController.navigate(Screen.OtherScreen) })
                    }
                    composable<Screen.OtherScreen> {
                        OtherScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    A6StarterTheme {
        Greeting("Android")
    }
}