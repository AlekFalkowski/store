package dev.falkow.blanco.nodes.catalog.types

import kotlinx.serialization.Serializable

@Serializable
data class Option(
    val label: String,
    val value: String,
    val quickInfo: String? = null,
    val blockingOptionIds: List<String>? = null,
)