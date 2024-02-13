package dev.falkow.blanco.nodes.account.model.states

internal sealed interface StableContentState {

    data object NotAuthorized : StableContentState

    data object AwaitingEmailConfirmation : StableContentState

    data class Authorized(
        val value: dev.falkow.blanco.shared.types.AccountData
        // val value: StateFlow<AccountData?>
    ) : StableContentState
}