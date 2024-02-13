package dev.falkow.blanco

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import dagger.hilt.android.AndroidEntryPoint
import dev.falkow.blanco.layout.display.AppLayout
import dev.falkow.blanco.shared.config.PROJECT_LOG_TAG
import javax.inject.Inject

@AndroidEntryPoint
class AppActivity : ComponentActivity() {

    // lateinit var firebaseAnalytics: FirebaseAnalytics

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var dynamicLinks: FirebaseDynamicLinks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                // Get deep link from result (may be null if no link is found)
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

                // Confirm the link is a sign-in with email link.
                if (auth.isSignInWithEmailLink(deepLink.toString())) {
                    // Retrieve this from wherever you stored it
                    val email = "alekfalkowski@gmail.com"

                    // The client SDK will parse the code from the link for you.
                    auth.signInWithEmailLink(email, deepLink.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(PROJECT_LOG_TAG, "Successfully signed in with email link!")
                                // You can access the new user via result.getUser()
                                // Additional user info profile *not* available via:
                                // result.getAdditionalUserInfo().getProfile() == null
                                // You can check if the user is new or existing:
                                // result.getAdditionalUserInfo().isNewUser()
                            } else {
                                Log.e(
                                    PROJECT_LOG_TAG,
                                    "Error signing in with email link",
                                    task.exception
                                )
                            }
                        }
                }

                // Handle the deep link. For example, open the linked
                // content, or apply promotional credit to the user's
                // account.
                // ...
            }
            .addOnFailureListener(this) { e ->
                Log.w(
                    PROJECT_LOG_TAG,
                    "getDynamicLink:onFailure",
                    e
                )
            }

        // supportActionBar?.hide()

        /** Transparent status bar: https://stackoverflow.com/questions/69305656/how-to-hide-actionbar-with-jetpack-compose */
        // WindowCompat.setDecorFitsSystemWindows(window, false)
        // window.setFlags(
        //     WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        //     WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        // )

        setContent { AppLayout(this) }

        // Как только система начнет вашу деятельность через фильтр намерений,
        // вы можете использовать данные, предоставленные, чтобы Intent определить,
        // что вам нужно отобразить. Вызовите методы getData() и getAction()
        // для получения данных и действий, связанных с входящим файлом Intent.
        // Вы можете вызывать эти методы в любой момент жизненного цикла действия,
        // но обычно это следует делать во время ранних обратных вызовов,
        // таких как onCreate()или onStart().
        // Следующий код получает данные из Intent:
        val action: String? = intent?.action
        val data: Uri? = intent?.data
        // Следуйте рекомендациям по проектированию,
        // описанным в разделе «Навигация с помощью функций «Назад» и «Вверх»,
        // чтобы ваше приложение соответствовало ожиданиям пользователей
        // в отношении обратной навигации после того,
        // как они войдут в ваше приложение по глубокой ссылке.
    }

    // override fun onNewIntent(intent: Intent) {
    //     super.onNewIntent(intent)
    //     checkIntent(intent)
    // }
}