package dev.falkow.blanco.nodes.catalog.types

import kotlinx.serialization.Serializable

@Serializable
data class FieldSet(
    val label: String? = null,
    val quickInfo: String? = null,
    val fieldList: List<Field>
)