package dev.falkow.blanco.nodes.account.options

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

internal class LogInWithEmailLinkAction @Inject constructor(
    private val repository: AccountRepository
) {

    private val _state = MutableStateFlow<LogInWithEmailLinkActionState>(
        LogInWithEmailLinkActionState.ReadyToStart
    )
    val state: StateFlow<LogInWithEmailLinkActionState> = _state.asStateFlow()

    fun resetError(): Unit {
        if (state.value is LogInWithEmailLinkActionState.InProgress) {
            return
        }
        _state.value = LogInWithEmailLinkActionState.ReadyToStart
    }

    suspend operator fun invoke(savedEmail: String?, emailLink: String?): Unit {
        if (state.value is LogInWithEmailLinkActionState.InProgress
            || savedEmail == null
            || emailLink == null
        ) {
            return
        }
        _state.value = LogInWithEmailLinkActionState.InProgress
        repository.logInWithEmailLink(savedEmail, emailLink)
        repository.deleteEmail()
        _state.value = LogInWithEmailLinkActionState.ReadyToStart

        // when (repository.sendLogInLinkToEmail(email)) {
        //     AuthWithEmailActionResult.Success -> {
        //         _state.value = LogInWithEmailLinkActionState.ReadyToStart
        //     }
        //
        //     AuthWithEmailActionResult.Error -> {
        //         _state.value = LogInWithEmailLinkActionState.Error(
        //             message = "Something went wrong..."
        //         )
        //     }
        // }
    }
}