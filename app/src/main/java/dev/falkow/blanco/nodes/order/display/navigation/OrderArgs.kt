package dev.falkow.blanco.nodes.order.display.navigation

import androidx.lifecycle.SavedStateHandle

internal class OrderArgs(val orderNavId: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        checkNotNull(savedStateHandle["orderNavId"]) as String
    )
}