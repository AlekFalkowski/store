package dev.falkow.blanco.layout.model

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.falkow.blanco.shared.config.ColorSchemes
import dev.falkow.blanco.shared.config.ColorThemes
import dev.falkow.blanco.shared.options.ObservePreferredAppColorSchemeOption
import dev.falkow.blanco.shared.options.SetPreferredAppColorSchemeOption
import dev.falkow.blanco.shared.options.ObservePreferredAppColorThemeOption
import dev.falkow.blanco.shared.options.SetPreferredAppColorThemeOption
import dev.falkow.blanco.shared.options.SetPreferredAppDynamicColorOption
import dev.falkow.blanco.shared.options.ObservePreferredAppDynamicColorOption
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppThemeViewModel @Inject constructor(
    private val setPreferredAppColorSchemeOption: SetPreferredAppColorSchemeOption,
    observePreferredAppColorSchemeOption: ObservePreferredAppColorSchemeOption,
    private val setPreferredAppColorThemeOption: SetPreferredAppColorThemeOption,
    observePreferredAppColorThemeOption: ObservePreferredAppColorThemeOption,
    private val setPreferredAppDynamicColorOption: SetPreferredAppDynamicColorOption,
    observePreferredAppDynamicColorOption: ObservePreferredAppDynamicColorOption,
) : ViewModel() {

    /** Preferred App Color Scheme. */

    val preferredAppColorScheme: StateFlow<ColorSchemes> =
        observePreferredAppColorSchemeOption.value
            .map {
                it ?: ColorSchemes.AUTO
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ColorSchemes.AUTO
            )

    fun setPreferredAppColorScheme(colorScheme: ColorSchemes): Unit {
        viewModelScope.launch { setPreferredAppColorSchemeOption(colorScheme) }
    }


    /** Preferred App Color Theme. */

    val preferredAppColorTheme: StateFlow<ColorThemes> =
        observePreferredAppColorThemeOption.value
            .map {
                it ?: ColorThemes.TEAL
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ColorThemes.TEAL
            )

    fun setPreferredAppColorTheme(colorTheme: ColorThemes): Unit {
        viewModelScope.launch { setPreferredAppColorThemeOption(colorTheme) }
    }


    /** Preferred App Dynamic Color. */

    val preferredAppDynamicColor: StateFlow<Boolean> =
        observePreferredAppDynamicColorOption.value
            .map {
                if (it != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) it
                else false
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )

    fun setPreferredAppDynamicColor(dynamicColor: Boolean) {
        viewModelScope.launch { setPreferredAppDynamicColorOption(dynamicColor) }
    }
}