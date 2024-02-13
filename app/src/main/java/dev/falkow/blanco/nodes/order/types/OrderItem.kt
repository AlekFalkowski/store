package dev.falkow.blanco.nodes.order.types

import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(
    val number: String,
    val product: OrderItemProduct,
    val price: OrderItemPrice,
    val qty: OrderItemQty,
    val amount: OrderItemAmount,
)
