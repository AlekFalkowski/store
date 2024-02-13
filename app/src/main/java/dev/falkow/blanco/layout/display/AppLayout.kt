package dev.falkow.blanco.layout.display

import android.app.Activity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.falkow.blanco.layout.display.navigation.AppNavHost
import dev.falkow.blanco.layout.display.panels.AppNavDrawer
import dev.falkow.blanco.layout.display.panels.AppTopBar
import dev.falkow.blanco.layout.model.AppLayoutViewModel
import dev.falkow.blanco.layout.display.templates.ClassicLayoutTemplate
import dev.falkow.blanco.shared.display.local_providers.LocalAppCoroutineScope
import dev.falkow.blanco.layout.display.theme.AppTheme
import dev.falkow.blanco.layout.model.AppThemeViewModel
import dev.falkow.blanco.shared.display.local_providers.LocalSnackbarHostState
import dev.falkow.blanco.shared.display.local_providers.LocalWindowWidthClass

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AppLayout(
    activity: Activity
) {
    val appLayoutViewModel = hiltViewModel<AppLayoutViewModel>()
    val appThemeViewModel = hiltViewModel<AppThemeViewModel>()
    val appCoroutineScope = rememberCoroutineScope()

    CompositionLocalProvider(
        LocalWindowWidthClass provides calculateWindowSizeClass(activity).widthSizeClass
    ) {
        CompositionLocalProvider(
            LocalAppCoroutineScope provides remember { appCoroutineScope }
        ) {
            CompositionLocalProvider(
                LocalSnackbarHostState provides remember { SnackbarHostState() }
            ) {
                AppTheme(
                    preferredColorScheme = appThemeViewModel.preferredAppColorScheme.collectAsStateWithLifecycle().value,
                    preferredColorTheme = appThemeViewModel.preferredAppColorTheme.collectAsStateWithLifecycle().value,
                    preferredDynamicColor = appThemeViewModel.preferredAppDynamicColor.collectAsStateWithLifecycle().value,
                ) {
                    ClassicLayoutTemplate(
                        topBarContent = { navController, currentFeatureNavRoute, onNavButtonClick ->
                            AppTopBar(
                                navController = navController,
                                currentFeatureNavRoute = currentFeatureNavRoute,
                                onNavButtonClick = onNavButtonClick,
                                // modifier = modifier,
                            )
                        },
                        navDrawerContent = { navController, currentFeatureNavRoute, currentFeatureFirstNavArg, closeDrawer ->
                            AppNavDrawer(
                                navController = navController,
                                currentFeatureNavRoute = currentFeatureNavRoute,
                                currentFeatureFirstNavArg = currentFeatureFirstNavArg,
                                closeDrawer = closeDrawer,
                                navDrawerContentIsLoading = appLayoutViewModel.navDrawerContentIsLoading.collectAsStateWithLifecycle().value,
                                navDrawerContentResult = appLayoutViewModel.navDrawerContentResult.collectAsStateWithLifecycle().value,
                                // modifier = modifier,
                            )
                        },
                        mainContent = { navController, modifier ->
                            // if (isAdminInterface) {
                            //     AdminNavHost(
                            //         navController = navController,
                            //         modifier = modifier,
                            //     )
                            // } else {
                            AppNavHost(
                                navController = navController,
                                modifier = modifier,
                            )
                            // }
                        },
                    )
                }
            }
        }
    }
}

// @Preview(showBackground = true, widthDp = 360)
// @Composable
// fun MainLayoutCompactPreview() {
//     AppTheme {
//         AppLayout(windowWidthClass = WindowWidthSizeClass.Compact)
//     }
// }
//
// @Preview(showBackground = true, widthDp = 700)
// @Composable
// fun MainLayoutMediumPreview() {
//     AppTheme {
//         AppLayout(windowWidthClass = WindowWidthSizeClass.Medium)
//     }
// }
//
// @Preview(showBackground = true, widthDp = 1000)
// @Composable
// fun MainLayoutExpandedPreview() {
//     AppTheme {
//         AppLayout(windowWidthClass = WindowWidthSizeClass.Expanded)
//     }
// }