package dev.falkow.blanco.nodes.catalog.model.states

import dev.falkow.blanco.nodes.catalog.types.DynamicContent

internal sealed interface DynamicContentState {

    data object Loading : DynamicContentState

    data class Success(
        val success: DynamicContent
    ) : DynamicContentState

    data object Error : DynamicContentState
}