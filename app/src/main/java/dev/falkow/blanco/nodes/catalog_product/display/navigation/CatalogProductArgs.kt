package dev.falkow.blanco.nodes.catalog_product.display.navigation

import androidx.lifecycle.SavedStateHandle

internal class CatalogProductArgs(val catalogNavId: String, val catalogProductNavId: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        checkNotNull(savedStateHandle["catalogNavId"]) as String,
        checkNotNull(savedStateHandle["catalogProductNavId"]) as String,
    )
}