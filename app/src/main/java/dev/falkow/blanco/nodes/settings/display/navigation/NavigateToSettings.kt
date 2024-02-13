package dev.falkow.blanco.nodes.settings.display.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import dev.falkow.blanco.shared.config.Features

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    try {
        this.navigate(Features.SETTINGS, navOptions)
    } catch (_: Exception) {
    }
}
