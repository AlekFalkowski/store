package dev.falkow.blanco.nodes.catalog.display.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.falkow.blanco.nodes.catalog.display.pages.CatalogPage
import dev.falkow.blanco.nodes.catalog.model.CatalogViewModel
import dev.falkow.blanco.shared.config.Features

fun NavGraphBuilder.catalogNavNode(
    navigateTo: (navId: String) -> Unit,
    navigateToHome: () -> Unit,
    navigateToCatalog: (catalogNavId: String) -> Unit,
    navigateToCatalogProduct: (catalogNavId: String, catalogProductNavId: String) -> Unit,
) {

    composable(
        route = "${Features.CATALOG}/{catalogNavId}",
        arguments = listOf<NamedNavArgument>(
            navArgument("catalogNavId") { type = NavType.StringType },
        ),
    ) {

        val viewModel: CatalogViewModel = hiltViewModel()

        CatalogPage(
            navigateTo = navigateTo,
            navigateToHome = navigateToHome,
            navigateToCatalog = navigateToCatalog,
            navigateToCatalogProduct = navigateToCatalogProduct,
            doStartInitialization = viewModel::doStartInitialization,
            stableContentState = viewModel.stableContentState.collectAsStateWithLifecycle().value,
            uriPrefix = viewModel.uriPrefix,
            queryString = viewModel.queryStringState.collectAsStateWithLifecycle().value,
            cardListDisplayFormState = viewModel.cardListDisplayFormState.collectAsStateWithLifecycle().value,
            setCardListDisplayForm = viewModel::setCardListDisplayForm,
            textFieldsStates = viewModel.textFieldsStates,
            singleChoiceFieldsStates = viewModel.singleChoiceFieldsStates,
            multiChoiceFieldsStates = viewModel.multiChoiceFieldsStates,
            dynamicContentState = viewModel.dynamicContentState.collectAsStateWithLifecycle().value,
            getDynamicContent = viewModel::getDynamicContent,
            resetFieldStates = viewModel::resetFieldStates,
        )

        // Surface(
        //     modifier = Modifier.fillMaxSize(),
        //     color = Color.Black.copy(alpha = 0.6f),
        // ) {
        //     Text(
        //         // modifier = Modifier.align(Alignment.Center),
        //         text = "Loading..."
        //     )
        // }
    }
}