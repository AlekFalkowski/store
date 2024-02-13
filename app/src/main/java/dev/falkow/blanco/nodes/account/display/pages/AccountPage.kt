package dev.falkow.blanco.nodes.account.display.pages

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import dev.falkow.blanco.R
import dev.falkow.blanco.nodes.account.model.states.StableContentState
import dev.falkow.blanco.nodes.account.options.LogInWithEmailLinkActionState
import dev.falkow.blanco.nodes.account.options.LogOutActionState
import dev.falkow.blanco.nodes.account.options.SendLogInLinkToEmailActionState
import dev.falkow.blanco.shared.types.AccountData
import dev.falkow.blanco.shared.display.templates.BasicPageTemplate
import dev.falkow.blanco.shared.display.sections.Title

@Composable
internal fun AccountPage(
    stableContentState: StableContentState,
    sendLogInLinkToEmailActionState: SendLogInLinkToEmailActionState,
    sendLogInLinkToEmail: (email: String) -> Unit,
    resetSendLogInLinkToEmailActionError: () -> Unit,
    logInWithEmailLinkActionState: LogInWithEmailLinkActionState,
    logInWithEmailLink: () -> Unit,
    resetLogInWithEmailLinkActionError: () -> Unit,
    logOutActionState: LogOutActionState,
    logOut: () -> Unit,
) {

    val context = LocalContext.current

    val webIntent: Intent = Uri.parse("https://www.falkow.dev").let { webpage ->
        Intent(Intent.ACTION_VIEW, webpage)
    }

    var email: String by rememberSaveable { mutableStateOf("") }

    BasicPageTemplate(
        secondColumnContent = { modifier: Modifier, contentMaxWidth ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {

                Title(title = stringResource(R.string.Account_Page))

                when (stableContentState) {
                    is StableContentState.NotAuthorized -> {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = stringResource(R.string.Login_or_register_using_your_email_),
                        )
                        OutlinedTextField(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            label = { Text(text = "Email") },
                            value = email,
                            onValueChange = {
                                if (sendLogInLinkToEmailActionState !is SendLogInLinkToEmailActionState.Error) {
                                    resetSendLogInLinkToEmailActionError()
                                }
                                email = it
                            },
                            isError = sendLogInLinkToEmailActionState is SendLogInLinkToEmailActionState.Error,
                            supportingText = {
                                if (sendLogInLinkToEmailActionState is SendLogInLinkToEmailActionState.Error && sendLogInLinkToEmailActionState.badEmail != null) {
                                    Text(text = sendLogInLinkToEmailActionState.badEmail)
                                }
                            },
                        )
                        Button(
                            onClick = { sendLogInLinkToEmail(email) },
                            enabled = sendLogInLinkToEmailActionState is SendLogInLinkToEmailActionState.ReadyToStart,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        ) {
                            Text(
                                text = when (sendLogInLinkToEmailActionState) {
                                    is SendLogInLinkToEmailActionState.InProgress -> "Trying..."
                                    else -> stringResource(R.string.Next)
                                }
                            )
                        }
                    }

                    is StableContentState.AwaitingEmailConfirmation -> {
                        Text(
                            text = stringResource(R.string.Confirm_email_address_by_clicking_on_the_link, email),
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        )
                        Button(
                            onClick = { logInWithEmailLink() },
                            enabled = logInWithEmailLinkActionState is LogInWithEmailLinkActionState.ReadyToStart,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        ) {
                            Text(
                                text = when (logInWithEmailLinkActionState) {
                                    is LogInWithEmailLinkActionState.InProgress -> "Trying..."
                                    else -> stringResource(R.string.Next)
                                }
                            )
                        }
                        Button(
                            onClick = { TODO() },
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        ) {
                            Text(text = stringResource(R.string.Cancel))
                        }
                    }

                    is StableContentState.Authorized -> {

                        val accountData: AccountData = stableContentState.value

                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "email: ${accountData.email}"
                        )
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "name: ${accountData.displayName}"
                        )
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "emailVerified: ${accountData.emailVerified}"
                        )
                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = { /*TODO*/ }
                        ) {
                            Text(text = "Change name")
                        }
                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = { /*TODO*/ }
                        ) {
                            Text(text = "Change password")
                        }
                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            enabled = logOutActionState is LogOutActionState.ReadyToStart,
                            onClick = { logOut() }
                        ) {
                            Text(
                                text = when (logOutActionState) {
                                    is LogOutActionState.InProgress -> "Trying..."
                                    else -> stringResource(R.string.Logout)
                                }
                            )
                        }
                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = { context.startActivity(webIntent) }
                        ) {
                            Text(text = "START INTENT")
                        }
                    }
                }

                // FooterSection()
            }
        }
    )
}