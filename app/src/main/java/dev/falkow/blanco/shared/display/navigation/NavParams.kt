package dev.falkow.blanco.shared.display.navigation

import kotlinx.serialization.Serializable

@Serializable
data class NavParams(
    val template: String,
    val stableContentUri: String,
    val dynamicContentUri: String,
)
