package com.example.a6starter.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.a6starter.ui.screens.main.Screen
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
sealed class CommonViewModelEffect {
    @Serializable
    data class NavigationEffect(val destination: Screen) : CommonViewModelEffect()
}

@Singleton
class CommonViewModelEffectsRepository @Inject constructor() {
    private val _effects = Channel<CommonViewModelEffect>()
    val effectsFlow = _effects.receiveAsFlow()

    suspend fun sendEffect(effect: CommonViewModelEffect) {
        _effects.send(effect)
    }
}

@Composable
fun CommonViewModelEffectHandler(
    effectFlow: Flow<CommonViewModelEffect>,
    effectHandler: (effect: CommonViewModelEffect) -> Unit
) {
    LaunchedEffect(Unit) {
        effectFlow.collect {
            effectHandler(it)
        }
    }
}

// Allow for injection into Composables
@EntryPoint
@InstallIn(SingletonComponent::class)
interface CommonViewModelEffectsRepositoryEntryPoint {
    fun getCommonViewModelEffectsRepository(): CommonViewModelEffectsRepository
}

@Composable
fun commonViewModelEffectsRepository(): CommonViewModelEffectsRepository {
    val context = LocalContext.current
    val commonViewModelEffectsRepository =
        EntryPointAccessors.fromApplication<CommonViewModelEffectsRepositoryEntryPoint>(
            context.applicationContext,
        ).getCommonViewModelEffectsRepository()
    return commonViewModelEffectsRepository
}