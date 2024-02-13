package dev.falkow.blanco.nodes.account.options

internal interface LogOutActionState {

    data object ReadyToStart : LogOutActionState

    data object InProgress : LogOutActionState

    data class Error(
        val message: String? = null,
    ) : LogOutActionState
}