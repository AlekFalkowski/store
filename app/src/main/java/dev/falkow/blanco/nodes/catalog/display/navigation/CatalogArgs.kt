package dev.falkow.blanco.nodes.catalog.display.navigation

import androidx.lifecycle.SavedStateHandle

internal class CatalogArgs(val catalogNavId: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        checkNotNull(savedStateHandle["catalogNavId"]) as String
    )
}