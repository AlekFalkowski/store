package dev.falkow.blanco.nodes.account.options

import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class SendLogInLinkToEmailAction @Inject constructor(
    private val repository: AccountRepository,
) {

    private val _state = MutableStateFlow<SendLogInLinkToEmailActionState>(
        SendLogInLinkToEmailActionState.ReadyToStart
    )
    val state: StateFlow<SendLogInLinkToEmailActionState> = _state.asStateFlow()

    fun resetError(): Unit {
        if (state.value is SendLogInLinkToEmailActionState.InProgress) {
            return
        }
        _state.value = SendLogInLinkToEmailActionState.ReadyToStart
    }

    suspend operator fun invoke(email: String): Unit {
        if (state.value is SendLogInLinkToEmailActionState.InProgress) {
            return
        }
        _state.value = SendLogInLinkToEmailActionState.InProgress
        repository.sendLogInLinkToEmail(email)
        repository.saveEmail(email)
        _state.value = SendLogInLinkToEmailActionState.ReadyToStart

        // when (repository.sendLogInLinkToEmail(email)) {
        //     AuthWithEmailActionResult.Success -> {
        //         _state.value = SendLogInLinkToEmailActionState.ReadyToStart
        //     }
        //
        //     AuthWithEmailActionResult.Error -> {
        //         _state.value = SendLogInLinkToEmailActionState.Error(
        //             badEmail = "Something went wrong..."
        //         )
        //     }
        // }
    }


    // val allowed: Flow<Boolean> =
    //     accountDataSignal.value.map {
    //         it == null
    //     }
    //
    // private val _state = MutableStateFlow<AuthWithEmailActionState>(
    //     if (repository.getEmailAwaitingConfirmationValue() == true) {
    //         AuthWithEmailActionState.InProgress
    //     } else {
    //         AuthWithEmailActionState.NeverStarted
    //     }
    // )
    // val state: StateFlow<AuthWithEmailActionState> = _state.asStateFlow()
    //
    // // falkow.page.link
    // // Это домен динамических ссылок.
    // // Он создан в разделе Dynamic Links
    // // и добавлен в список авторизованных доменов в разделе Authentication.
    //
    // // http://falkow.page.link
    // // Это префикс URL динамической ссылки
    //
    // // private val url = "http://www.example.com/verify?uid=" + user.uid
    // private val actionCodeSettings = ActionCodeSettings.newBuilder()
    //     // URL you want to redirect back to. The domain (www.example.com) for this
    //     // URL must be whitelisted in the Firebase Console.
    //     .setUrl("http://falkow.page.link")
    //     // This must be true
    //     .setHandleCodeInApp(true)
    //     // .setIOSBundleId("com.example.ios")
    //     // The default for this is populated with the current android package name.
    //     .setAndroidPackageName(
    //         "dev.falkow.blanco",
    //         false, // Возвращает предпочтение, следует ли пытаться установить приложение, если оно еще не установлено на устройстве.
    //         null
    //     )
    //     .build()
    //
    //
    // suspend operator fun invoke(email: String): Unit {
    //
    //     if (state.value is AuthWithEmailActionState.InProgress || !allowed.first()) {
    //         return
    //     }
    //
    //     _state.value = AuthWithEmailActionState.InProgress
    //
    //     when (repository.authWithEmail(email, actionCodeSettings)) {
    //         AuthWithEmailActionResult.Success -> {
    //             _state.value = AuthWithEmailActionState.Success
    //         }
    //
    //         AuthWithEmailActionResult.Error -> {
    //             _state.value = AuthWithEmailActionState.Error(
    //                 badEmail = "Something went wrong..."
    //             )
    //         }
    //     }
    // }
}