package dev.falkow.blanco.nodes.home.model.states

import dev.falkow.blanco.nodes.home.types.StableContent

internal sealed interface StableContentState {
    data object Loading : StableContentState
    data object Error : StableContentState
    data class Success(
        val stableContent: StableContent
    ) : StableContentState
}