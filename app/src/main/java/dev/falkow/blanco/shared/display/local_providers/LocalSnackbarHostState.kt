package dev.falkow.blanco.shared.display.local_providers

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackbarHostState = staticCompositionLocalOf { SnackbarHostState() }