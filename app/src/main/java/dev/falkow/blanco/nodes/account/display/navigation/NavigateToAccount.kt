package dev.falkow.blanco.nodes.account.display.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import dev.falkow.blanco.shared.config.Features

fun NavController.navigateToAccount(navOptions: NavOptions? = null) {
    try {
        this.navigate(Features.ACCOUNT, navOptions)
    } catch (_: Exception) {
    }
}