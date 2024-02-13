package dev.falkow.blanco.nodes.catalog_product.model.states

import dev.falkow.blanco.nodes.catalog_product.types.StableContent

internal sealed interface CatalogProductState {
    data object Loading : CatalogProductState
    data object Error : CatalogProductState
    data class Success(
        val stableContent: StableContent
    ) : CatalogProductState
}