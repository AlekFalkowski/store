package dev.falkow.blanco.nodes.catalog.display.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import dev.falkow.blanco.shared.config.Features

fun NavController.navigateToCatalog(catalogNavId: String, navOptions: NavOptions? = null) {
    try {
        this.navigate("${Features.CATALOG}/${Uri.encode(catalogNavId)}", navOptions)
    } catch (_: Exception) {
    }
}