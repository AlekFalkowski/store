package dev.falkow.blanco.shared.types

data class FinishedProductCard(
    val catalogNavId: String,
    val catalogProductNavId: String,
    val title: String,
    val price: String,
    val imageSrc: String,
) : ICatalogCard