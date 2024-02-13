package dev.falkow.blanco.nodes.order.display.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateToOrder(orderNavId: String, navOptions: NavOptions? = null) {
    try {
        this.navigate("orders/${Uri.encode(orderNavId)}", navOptions)
    } catch (_: Exception) {
    }
}