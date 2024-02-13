package dev.falkow.blanco.shared.types

data class CustomizableProductCard(
    val catalogNavId: String,
    val catalogProductNavId: String,
    val title: String,
    val description: String,
    val imageSrc: String,
) : ICatalogCard