package dev.falkow.blanco.nodes.catalog_product.types

data class StableContent(
    //val refId: String,
    //val imageSrc: String,
    val imageGalleryList: List<ImageGallery>,
    val title: String,
    val article: String,
    val price: String,
    val specification: String,
)
