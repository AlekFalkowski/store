package dev.falkow.blanco.nodes.account.display.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.falkow.blanco.nodes.account.display.pages.AccountPage
import dev.falkow.blanco.nodes.account.model.AccountViewModel
import dev.falkow.blanco.shared.config.Features

fun NavGraphBuilder.accountNavNode(
    navigateToHome: () -> Unit,
) {

    composable(route = Features.ACCOUNT) {

        val frontModel = hiltViewModel<AccountViewModel>()

        AccountPage(
            stableContentState = frontModel.stableContentState.collectAsStateWithLifecycle().value,
            sendLogInLinkToEmailActionState = frontModel.sendLogInLinkToEmailActionState.collectAsStateWithLifecycle().value,
            sendLogInLinkToEmail = frontModel::sendLogInLinkToEmail,
            resetSendLogInLinkToEmailActionError = frontModel::resetLogInWithEmailLinkActionError,
            logInWithEmailLinkActionState = frontModel.logInWithEmailLinkActionState.collectAsStateWithLifecycle().value,
            logInWithEmailLink = frontModel::logInWithEmailLink,
            resetLogInWithEmailLinkActionError = frontModel::resetLogInWithEmailLinkActionError,
            logOutActionState = frontModel.logOutActionState.collectAsStateWithLifecycle().value,
            logOut = frontModel::logOut,
        )
    }
}