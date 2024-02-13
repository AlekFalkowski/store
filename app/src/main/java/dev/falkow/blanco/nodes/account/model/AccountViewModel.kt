package dev.falkow.blanco.nodes.account.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.falkow.blanco.nodes.account.options.LogInWithEmailLinkAction
import dev.falkow.blanco.nodes.account.options.LogInWithEmailLinkActionState
import dev.falkow.blanco.nodes.account.options.LogOutAction
import dev.falkow.blanco.nodes.account.options.LogOutActionState
import dev.falkow.blanco.nodes.account.options.SavedEmailSignal
import dev.falkow.blanco.nodes.account.options.SendLogInLinkToEmailAction
import dev.falkow.blanco.nodes.account.options.SendLogInLinkToEmailActionState
import dev.falkow.blanco.nodes.account.model.states.StableContentState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
internal class AccountViewModel @Inject constructor(
    observeAccountData: dev.falkow.blanco.shared.options.ObserveAccountData,
    private val savedEmailSignal: SavedEmailSignal,
    private val sendLogInLinkToEmailAction: SendLogInLinkToEmailAction,
    private val logInWithEmailLinkAction: LogInWithEmailLinkAction,
    private val logOutAction: LogOutAction,
) : ViewModel() {

    /** Account Front State. */

    val stableContentState: StateFlow<StableContentState> =
        combine(observeAccountData.value, savedEmailSignal.value) { accountData, savedEmail ->
            when {
                accountData != null && accountData.emailVerified -> StableContentState.Authorized(
                    value = accountData
                    // value = accountDataSignal.value.stateIn(
                    //     scope = viewModelScope,
                    //     started = SharingStarted.WhileSubscribed(5_000),
                    //     initialValue = null
                    // )
                )

                savedEmail != null -> StableContentState.AwaitingEmailConfirmation
                else -> StableContentState.NotAuthorized
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = StableContentState.NotAuthorized,
        )


    /** Send LogIn Link To Email. */

    val sendLogInLinkToEmailActionState: StateFlow<SendLogInLinkToEmailActionState> =
        sendLogInLinkToEmailAction.state

    fun sendLogInLinkToEmail(email: String): Unit {
        viewModelScope.launch {
            sendLogInLinkToEmailAction(email)
        }
    }

    fun resetSendLogInLinkToEmailActionError(): Unit {
        sendLogInLinkToEmailAction.resetError()
    }


    /** LogIn With Email Link. */

    val logInWithEmailLinkActionState: StateFlow<LogInWithEmailLinkActionState> =
        logInWithEmailLinkAction.state

    fun logInWithEmailLink(): Unit {
        viewModelScope.launch {
            logInWithEmailLinkAction(
                savedEmail = savedEmailSignal.value.first(),
                emailLink = ""
            )
        }
    }

    fun resetLogInWithEmailLinkActionError(): Unit {
        logInWithEmailLinkAction.resetError()
    }


    /** Log Out. */

    val logOutActionState: StateFlow<LogOutActionState> =
        logOutAction.state

    fun logOut(): Unit {
        viewModelScope.launch { logOutAction() }
    }
}