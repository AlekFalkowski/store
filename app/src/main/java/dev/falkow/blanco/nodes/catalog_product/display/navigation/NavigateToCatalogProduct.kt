package dev.falkow.blanco.nodes.catalog_product.display.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import dev.falkow.blanco.shared.config.Features

fun NavController.navigateToCatalogProduct(catalogNavId: String, catalogProductNavId: String, navOptions: NavOptions? = null) {
    try {
        this.navigate(
            "${Features.CATALOG_PRODUCT}/${Uri.encode(catalogNavId)}/${
                Uri.encode(
                    catalogProductNavId
                )
            }", navOptions
        )
    } catch (_: Exception) {
    }
}