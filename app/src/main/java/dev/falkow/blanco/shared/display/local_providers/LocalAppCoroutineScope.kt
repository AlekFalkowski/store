package dev.falkow.blanco.shared.display.local_providers

import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope

@OptIn(DelicateCoroutinesApi::class)
val LocalAppCoroutineScope = staticCompositionLocalOf<CoroutineScope> { GlobalScope }