package dev.falkow.blanco.nodes.catalog.model.states

import dev.falkow.blanco.nodes.catalog.types.StableContent

internal sealed interface StableContentState {

    data object Loading : StableContentState

    data class Success(
        val success: StableContent
    ) : StableContentState

    data object Error : StableContentState
}