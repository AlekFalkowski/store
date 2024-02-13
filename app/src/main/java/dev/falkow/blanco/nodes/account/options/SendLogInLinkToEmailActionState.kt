package dev.falkow.blanco.nodes.account.options

internal interface SendLogInLinkToEmailActionState {

    data object ReadyToStart : SendLogInLinkToEmailActionState

    data object InProgress : SendLogInLinkToEmailActionState

    data class Error(
        val badEmail: String? = null,
        val badPassword: String? = null,
        val message: String? = null,
    ) : SendLogInLinkToEmailActionState
}