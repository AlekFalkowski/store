package dev.falkow.blanco.shared.display.local_providers

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.staticCompositionLocalOf

val LocalWindowWidthClass = staticCompositionLocalOf { WindowWidthSizeClass.Compact }