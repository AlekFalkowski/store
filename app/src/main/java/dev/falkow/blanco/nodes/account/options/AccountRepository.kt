package dev.falkow.blanco.nodes.account.options

import android.util.Log
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import dev.falkow.blanco.shared.storages.IKeyValueLocalStorage
import dev.falkow.blanco.shared.storages.KeyValueTable
import dev.falkow.blanco.shared.config.PROJECT_LOG_TAG
import dev.falkow.blanco.shared.storages.Keys
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val keyValueLocalStore: IKeyValueLocalStorage,
) : dev.falkow.blanco.shared.options.IAccountRepository {

    /** Account Data. */

    override val accountData: Flow<dev.falkow.blanco.shared.types.AccountData?> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(
                it.currentUser?.let {
                    dev.falkow.blanco.shared.types.AccountData(
                        displayName = it.displayName,
                        email = it.email,
                        photoUrl = it.photoUrl,
                        emailVerified = it.isEmailVerified,
                        uid = it.uid,
                    )
                }
            )
        }
        firebaseAuth.addAuthStateListener(authStateListener)
        awaitClose {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }

    /** Send LogIn Link To Email. */

    // Ссылка на подтверждение владения почтовым ящиком
    // https://falkow.page.link/? // <- это динамическая ссылка
    //      link = https://blanco-d4043.firebaseapp.com/__/auth/action? // <- это глубокая ссылка
    //          apiKey %3D AIzaSyAwQ1khF15jOb57cmn_RjOgLrVTz-_0CIM%26
    //          mode %3D signIn%26
    //          oobCode %3D qfXnocuVypE5iqBAjrR4PM0v03mLlsid0irNcpHbMEQAAAGLTtrcMg%26
    //          continueUrl %3D http://falkow.page.link%26 // <- это continueUrl параметр запроса в глубокой ссылке динамической ссылки. А когда ссылка обрабатывается в виджетах веб-действий, это глубокая ссылка.
    //          lang %3D en
    //      &apn = dev.falkow.blanco
    //      &amv
    //      &afl = https://blanco-d4043.firebaseapp.com/__/auth/action?apiKey%3DAIzaSyAwQ1khF15jOb57cmn_RjOgLrVTz-_0CIM%26mode%3DsignIn%26oobCode%3DqfXnocuVypE5iqBAjrR4PM0v03mLlsid0irNcpHbMEQAAAGLTtrcMg%26continueUrl%3Dhttp://falkow.page.link%26lang%3Den

    // Так ссылка обрабатывается в виджетах веб-действий
    // https://falkow.page.link/?
    //      apiKey=AIzaSyAwQ1khF15jOb57cmn_RjOgLrVTz-_0CIM
    //     &oobCode=MqQRdqVc7HtFhmmy8CkA9BRkpXOcGEn5z7sVaqbdAqIAAAGLTtaHPQ
    //     &mode=signIn
    //     &lang=en

    // https://falkow.page.link/?link=https://blanco-d4043.firebaseapp.com/__/auth/action?apiKey%3DAIzaSyAwQ1khF15jOb57cmn_RjOgLrVTz-_0CIM%26mode%3DsignIn%26oobCode%3DqfXnocuVypE5iqBAjrR4PM0v03mLlsid0irNcpHbMEQAAAGLTtrcMg%26continueUrl%3Dhttp://falkow.dev%26lang%3Den&apn=dev.falkow.blanco&amv&afl=https://blanco-d4043.firebaseapp.com/__/auth/action?apiKey%3DAIzaSyAwQ1khF15jOb57cmn_RjOgLrVTz-_0CIM%26mode%3DsignIn%26oobCode%3DqfXnocuVypE5iqBAjrR4PM0v03mLlsid0irNcpHbMEQAAAGLTtrcMg%26continueUrl%3Dhttp://falkow.dev?falcon%3Dfalcon%26lang%3Den

    // falkow.page.link
    // Это домен динамических ссылок.
    // Он создан в разделе Dynamic Links
    //

    // http://falkow.page.link
    // Это префикс URL динамической ссылки

    fun sendLogInLinkToEmail(email: String): Unit {

        val settings = ActionCodeSettings.newBuilder()
            // .setDynamicLinkDomain("falkow.page.link")
            // URL you want to redirect back to. The domain (www.example.com) for this
            // URL must be whitelisted in the Firebase Console.
            // Глубокая ссылка для внедрения и любое дополнительное состояние, которое необходимо передать.
            // Ссылка перенаправит пользователя на этот URL-адрес, если приложение не установлено на его устройстве и его не удалось установить
            // continueUrl
            // Должен быть добавлен в список авторизованных доменов в разделе Authentication.
            // .setUrl("http://falkow.dev/blanco/finishSignUp")
            .setUrl("http://falkow.page.link")
            // .setUrl("http://www.example.com/verify?uid=" + user.uid)
            // This must be true
            .setHandleCodeInApp(true)
            // .setIOSBundleId("com.example.ios")
            // The default for this is populated with the current android package name.
            .setAndroidPackageName(
                "dev.falkow.blanco",
                false, // Возвращает предпочтение, следует ли пытаться установить приложение, если оно еще не установлено на устройстве.
                null
            )
            .build()

        firebaseAuth.sendSignInLinkToEmail(email, settings)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(PROJECT_LOG_TAG, "Email sent.")
                }
            }
            .addOnFailureListener { e ->
                Log.w(PROJECT_LOG_TAG, "Email is not sent", e)
            }
    }


    /** LogIn With Email Link. */

    fun logInWithEmailLink(savedEmail: String, emailLink: String): Unit {
        // // Confirm the link is a sign-in with email link.
        // firebaseAuth.signInWithCustomToken()
        //
        // if (firebaseAuth.isSignInWithEmailLink(emailLink)) {
        //
        //     // The client SDK will parse the code from the link for you.
        //     firebaseAuth.signInWithEmailLink(savedEmail, emailLink)
        //         .addOnCompleteListener { task ->
        //             if (task.isSuccessful) {
        //                 Log.d(PROJECT_LOG_TAG, "Successfully signed in with email link!")
        //             } else {
        //                 Log.e(PROJECT_LOG_TAG, "Error signing in with email link", task.exception)
        //             }
        //         }
        // }
    }


    /** Log Out. */

    suspend fun logOutAccount(): Unit {
        firebaseAuth.signOut()
    }


    /** Remember Email. */

    val savedEmail: Flow<String?> =
        keyValueLocalStore.observeValueByKey(Keys.ACCOUNT_EMAIL)

    // suspend fun getEmail(): String? {
    //     return keyValueLocalStorage.getSetting(Keys.ACCOUNT_EMAIL)
    // }

    suspend fun saveEmail(email: String): Unit {
        keyValueLocalStore.setValueByKey(
            KeyValueTable(
                key = Keys.ACCOUNT_EMAIL,
                value = email,
            )
        )
    }

    suspend fun deleteEmail(): Unit {
        keyValueLocalStore.deleteValueByKey(Keys.ACCOUNT_EMAIL)
    }
}