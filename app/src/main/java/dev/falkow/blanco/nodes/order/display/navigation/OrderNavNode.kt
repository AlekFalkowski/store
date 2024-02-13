package dev.falkow.blanco.nodes.order.display.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import dev.falkow.blanco.nodes.order.display.pages.OrderPage
import dev.falkow.blanco.nodes.order.model.OrderViewModel

fun NavGraphBuilder.orderNavNode(
    navigateTo: (navId: String) -> Unit,
    navigateToHome: () -> Unit,
    navigateToOrderProduct: (orderNavId: String, orderProductNavId: String) -> Unit,
) {

    composable(
        route = "orders/{orderNavId}",
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "http://dev.falkow.blanco/purchase-orders/{orderNavId}"
                // action = Intent.ACTION_VIEW
            },
            navDeepLink {
                uriPattern = "http://dev.falkow.blanco/purchase-orders/{orderNavId}/"
            },
            navDeepLink {
                uriPattern =
                    "http://dev.falkow.blanco/purchase-orders/{orderNavId}?{queryString}"
            },
            navDeepLink {
                uriPattern =
                    "http://dev.falkow.blanco/purchase-orders/{orderNavId}/?{queryString}"
            }
        ),
        arguments = listOf<NamedNavArgument>(
            navArgument("orderNavId") {
                type = NavType.StringType
            },
            navArgument("queryString") {
                type = NavType.StringType
                // nullable = true OR defaultValue = ""
                defaultValue = ""
            },
        ),
    ) {
        // val deepLinkIntent: Intent? =
        //     it.arguments?.getParcelable(
        //         NavController.KEY_DEEP_LINK_INTENT
        //     )
        // val fullUri = deepLinkIntent?.data
        // println(fullUri)

        val viewModel: OrderViewModel = hiltViewModel()

        OrderPage(
            navigateTo = navigateTo,
            navigateToHome = navigateToHome,
            navigateToOrderProduct = navigateToOrderProduct,
            doStartInitialization = viewModel::doStartInitialization,
            stableContentState = viewModel.stableContentState.collectAsStateWithLifecycle().value,
        )
    }
}