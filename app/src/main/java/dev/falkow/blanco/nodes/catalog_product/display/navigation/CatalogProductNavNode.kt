package dev.falkow.blanco.nodes.catalog_product.display.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.falkow.blanco.nodes.catalog_product.display.pages.CatalogProductPage
import dev.falkow.blanco.nodes.catalog_product.model.CatalogProductViewModel
import dev.falkow.blanco.shared.config.Features

fun NavGraphBuilder.catalogProductNavNode(
    navigateToHome: () -> Unit,
) {
    composable(
        route = "${Features.CATALOG_PRODUCT}/{catalogNavId}/{catalogProductNavId}",
        arguments = listOf<NamedNavArgument>(
            navArgument("catalogNavId") { type = NavType.StringType },
            navArgument("catalogProductNavId") { type = NavType.StringType },
        ),
    ) {

        // val viewModel: CatalogProductViewModel = hiltViewModel()
        // when(viewModel.productType.collectAsStateWithLifecycle().value) {
        //      "zebra" -> {
        //          ZebraProduct
        //      }
        //      "roller" -> {
        //          RollerProduct
        //      }
        // }


        val viewModel: CatalogProductViewModel = hiltViewModel()

        CatalogProductPage(
            navigateToHome = navigateToHome,
            // pageName = "Catalog: ${it.arguments?.getString(catalogNavIdArg)}, CatalogProduct: ${it.arguments?.getString(catalogProductNavIdArg)},",

            catalogNavId = viewModel.catalogNavId,
            catalogProductNavId = viewModel.catalogProductNavId,
            catalogProductState = viewModel.catalogProductState,
            preferredIsGalleryButtonsDisplayEnable = viewModel.preferredIsGalleryButtonsDisplayEnable.collectAsStateWithLifecycle().value,
            setPreferredIsGalleryButtonsDisplayEnable = viewModel::setPreferredIsGalleryButtonsDisplayEnable,
            // viewModelScope = viewModel.viewModelScope,
        )
    }
}