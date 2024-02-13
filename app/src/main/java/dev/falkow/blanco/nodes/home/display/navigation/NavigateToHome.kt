package dev.falkow.blanco.nodes.home.display.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import dev.falkow.blanco.shared.config.Features

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    try {
        this.navigate(Features.HOME, navOptions)
        // this.popBackStack(Feature.HOME, false)
    } catch (_: Exception) {
    }
}