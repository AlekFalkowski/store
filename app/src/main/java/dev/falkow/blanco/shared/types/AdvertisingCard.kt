package dev.falkow.blanco.shared.types

data class AdvertisingCard(
    val navId: String,
    val title: String,
    val description: String,
    val imageSrc: Int,
) : ICatalogCard