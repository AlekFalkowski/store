package dev.falkow.blanco.nodes.order.model.states

import dev.falkow.blanco.nodes.order.types.StableContent

internal sealed interface StableContentState {

    data object Loading : StableContentState

    data class Success(
        val success: StableContent
    ) : StableContentState

    data object Error : StableContentState
}