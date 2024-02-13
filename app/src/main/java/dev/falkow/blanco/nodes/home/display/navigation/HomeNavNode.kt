package dev.falkow.blanco.nodes.home.display.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.falkow.blanco.nodes.home.display.pages.HomePage
import dev.falkow.blanco.nodes.home.model.HomeViewModel
import dev.falkow.blanco.shared.config.Features

fun NavGraphBuilder.homeNavNode(
    navigateToCatalog: (catalogNavId: String) -> Unit,
    navigateToOrder: (catalogNavId: String) -> Unit,
    // navigateToCatalogProduct: (catalogProductNavId: String) -> Unit,
) {
    composable(
        route = Features.HOME,
    ) {

        val viewModel: HomeViewModel = hiltViewModel()

        HomePage(
            navigateToCatalog = navigateToCatalog,
            // navigateToCatalogProduct = navigateToCatalogProduct,
            navigateToOrder = navigateToOrder,
            stableContentState = viewModel.stableContentState,
        )
    }
}