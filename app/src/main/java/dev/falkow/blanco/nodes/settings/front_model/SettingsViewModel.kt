package dev.falkow.blanco.nodes.settings.front_model

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    // private val appModification: AppModificationModel,
    // private val preferredAppColorSchemeInstaller: PreferredAppColorSchemeInstaller,
    // private val preferredAppColorSchemeProvider: PreferredAppColorSchemeProvider,
    // private val preferredAppColorThemeInstaller: PreferredAppColorThemeInstaller,
    // private val preferredAppColorThemeProvider: PreferredAppColorThemeProvider,
    // private val preferredAppDynamicColorInstaller: PreferredAppDynamicColorInstaller,
    // private val preferredAppDynamicColorProvider: PreferredAppDynamicColorProvider,
) : ViewModel() {

    // /** Preferred App Color Scheme. */
    //
    // val colorSchemes: Set<ColorSchemes> =
    //     appModification.colorSchemes
    //
    // val preferredAppColorScheme: StateFlow<ColorSchemes> =
    //     preferredAppColorSchemeProvider.value
    //         .stateIn(
    //             scope = viewModelScope,
    //             started = SharingStarted.WhileSubscribed(5_000),
    //             initialValue = colorSchemes.first()
    //         )
    //
    // fun setPreferredAppColorScheme(colorScheme: ColorSchemes): Unit {
    //     viewModelScope.launch { preferredAppColorSchemeInstaller.setValue(colorScheme) }
    // }
    //
    //
    // /** Preferred App Color Theme. */
    //
    // val colorThemes: Set<ColorThemes> =
    //     appModification.colorThemes
    //
    // val preferredAppColorTheme: StateFlow<ColorThemes> =
    //     preferredAppColorThemeProvider.value
    //         .stateIn(
    //             scope = viewModelScope,
    //             started = SharingStarted.WhileSubscribed(5_000),
    //             initialValue = colorThemes.first()
    //         )
    //
    // fun setPreferredAppColorTheme(colorTheme: ColorThemes): Unit {
    //     viewModelScope.launch { preferredAppColorThemeInstaller.setValue(colorTheme) }
    // }
    //
    //
    // /** Preferred App Dynamic Color. */
    //
    // val dynamicColorAvailable: Boolean =
    //     appModification.dynamicColorAvailable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    //
    // val preferredAppDynamicColor: StateFlow<Boolean> =
    //     preferredAppDynamicColorProvider.value
    //         .stateIn(
    //             scope = viewModelScope,
    //             started = SharingStarted.WhileSubscribed(5_000),
    //             initialValue = false
    //         )
    //
    // fun setPreferredAppDynamicColor(dynamicColor: Boolean) {
    //     viewModelScope.launch { preferredAppDynamicColorInstaller.setValue(dynamicColor) }
    // }
}