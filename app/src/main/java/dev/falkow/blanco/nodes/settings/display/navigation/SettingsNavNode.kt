package dev.falkow.blanco.nodes.settings.display.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.falkow.blanco.nodes.settings.display.pages.SettingsPage
import dev.falkow.blanco.nodes.settings.front_model.SettingsViewModel
import dev.falkow.blanco.shared.config.Features
import dev.falkow.blanco.layout.model.AppThemeViewModel

fun NavGraphBuilder.settingsNavNode(
    navigateToHome: () -> Unit,
) {
    composable(
        route = Features.SETTINGS,
    ) {

        val frontModel = hiltViewModel<SettingsViewModel>()
        val appThemeViewModel = hiltViewModel<AppThemeViewModel>()

        SettingsPage(
            // navigateToHome = navigateToHome,
        )
    }
}