package dev.falkow.blanco.layout.display.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.falkow.blanco.nodes.account.display.navigation.accountNavNode
import dev.falkow.blanco.nodes.catalog.display.navigation.catalogNavNode
import dev.falkow.blanco.nodes.catalog.display.navigation.navigateToCatalog
import dev.falkow.blanco.nodes.catalog_product.display.navigation.catalogProductNavNode
import dev.falkow.blanco.nodes.catalog_product.display.navigation.navigateToCatalogProduct
import dev.falkow.blanco.nodes.home.display.navigation.homeNavNode
import dev.falkow.blanco.nodes.home.display.navigation.navigateToHome
import dev.falkow.blanco.nodes.order.display.navigation.navigateToOrder
import dev.falkow.blanco.nodes.order.display.navigation.orderNavNode
import dev.falkow.blanco.nodes.settings.display.navigation.settingsNavNode
import dev.falkow.blanco.shared.config.Features

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Features.HOME,
        modifier = modifier
    ) {

        homeNavNode(
            navigateToCatalog = { catalogNavId ->
                navController.navigateToCatalog(catalogNavId)
            },
            navigateToOrder = { orderNavId ->
                navController.navigateToOrder(orderNavId)
            },
        )

        accountNavNode(
            // navController = navController,
            navigateToHome = {
                navController.navigateToHome()
            }
        )

        catalogNavNode(
            navigateTo = { navId -> },
            navigateToHome = {
                navController.navigateToHome()
            },
            navigateToCatalog = { catalogNavId ->
                navController.navigateToCatalog(catalogNavId)
            },
            navigateToCatalogProduct = { catalogNavId, catalogProductNavId ->
                navController.navigateToCatalogProduct(catalogNavId, catalogProductNavId)
            }
        )

        catalogProductNavNode(
            navigateToHome = {
                navController.navigateToHome()
            }
        )

        orderNavNode(
            navigateTo = { navId -> },
            navigateToHome = {
                navController.navigateToHome()
            },
            navigateToOrderProduct = { orderNavId, orderProductNavId ->
                // navController.navigateToOrderProduct(orderNavId, orderProductNavId)
            }
        )

        // orderProductNavNode(
        //     navigateToHome = {
        //         navController.navigateToHome()
        //     }
        // )

        settingsNavNode(
            navigateToHome = {
                navController.navigateToHome()
            },
        )
    }
}