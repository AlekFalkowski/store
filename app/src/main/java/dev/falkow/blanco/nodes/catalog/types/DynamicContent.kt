package dev.falkow.blanco.nodes.catalog.types

import dev.falkow.blanco.shared.types.ICatalogCard

internal data class DynamicContent(
    val cardList: List<ICatalogCard>
)