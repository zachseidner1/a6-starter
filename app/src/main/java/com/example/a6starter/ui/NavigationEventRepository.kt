package com.example.a6starter.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.a6starter.ui.screens.main.Screen
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NavigationEventRepository @Inject constructor() {
    private val _navigationEvents = Channel<Screen>()
    val navigationEventsFlow = _navigationEvents.receiveAsFlow()

    suspend fun sendNavigationEvent(destination: Screen) {
        _navigationEvents.send(destination)
    }
}

// Allow for injection into Composables
@EntryPoint
@InstallIn(SingletonComponent::class)
interface NavigationRepositoryEntryPoint {
    fun getNavigationEventRepository(): NavigationEventRepository
}

@Composable
fun navigationEventRepository(): NavigationEventRepository {
    val context = LocalContext.current
    val appStorePopupManager = EntryPointAccessors.fromApplication<NavigationRepositoryEntryPoint>(
        context.applicationContext,
    ).getNavigationEventRepository()
    return appStorePopupManager
}