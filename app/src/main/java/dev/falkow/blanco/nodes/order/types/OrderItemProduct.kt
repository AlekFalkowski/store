package dev.falkow.blanco.nodes.order.types

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemProduct(
    val name: String,
    val sku: String,
)