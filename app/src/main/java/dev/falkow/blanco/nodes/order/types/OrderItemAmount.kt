package dev.falkow.blanco.nodes.order.types

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemAmount(
    val number: String?,
)
