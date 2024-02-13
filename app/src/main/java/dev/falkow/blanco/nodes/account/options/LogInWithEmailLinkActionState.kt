package dev.falkow.blanco.nodes.account.options

internal interface LogInWithEmailLinkActionState {

    data object ReadyToStart : LogInWithEmailLinkActionState

    data object InProgress : LogInWithEmailLinkActionState

    data class Error(
        val message: String? = null,
    ) : LogInWithEmailLinkActionState
}