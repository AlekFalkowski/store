package dev.falkow.blanco.nodes.catalog.types

import kotlinx.serialization.Serializable

@Serializable
data class StableContent(
    val title: String,
    // val description: String,
    // val banner: Any,
    val filterConfig: List<FieldSet>,
)