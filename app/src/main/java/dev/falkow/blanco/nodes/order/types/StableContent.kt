package dev.falkow.blanco.nodes.order.types

import kotlinx.serialization.Serializable

@Serializable
data class StableContent(
    val number: String,
    val items: List<OrderItem>,
    val amount: String,
)