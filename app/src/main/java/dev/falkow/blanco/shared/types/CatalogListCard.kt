package dev.falkow.blanco.shared.types

data class CatalogListCard(
    val catalogNavId: String,
    val title: String,
    val description: String,
    val imageResId: Int,
) : ICatalogCard