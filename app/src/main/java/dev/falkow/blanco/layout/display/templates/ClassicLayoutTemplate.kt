package dev.falkow.blanco.layout.display.templates

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.falkow.blanco.shared.display.local_providers.LocalSnackbarHostState
import kotlinx.coroutines.launch

@Composable
fun ClassicLayoutTemplate(
    topBarContent: @Composable (
        navController: NavHostController,
        currentFeatureNavRoute: String?,
        onNavButtonClick: () -> Unit,
        // modifier: Modifier,
    ) -> Unit,
    navDrawerContent: @Composable (
        navController: NavHostController,
        currentFeatureNavRoute: String?,
        currentFeatureFirstNavArg: String?,
        closeDrawer: () -> Unit,
        // modifier: Modifier,
    ) -> Unit,
    mainContent: @Composable (
        navController: NavHostController,
        modifier: Modifier,
    ) -> Unit,
) {
    /**
     * Вы должны создать NavController в том месте в вашей компонуемой иерархии,
     * где все компонуемые объекты, которые должны ссылаться на него, имеют к нему доступ.
     * Вы можете создать NavController, используя метод rememberNavController().
     * Каждый NavController должен быть связан с одним NavHost компонуемым.
     * Перейти на другой маршрут: navController.navigate(NavPoint.Name.name).
     * Перейти на другой маршрут: navController.navigate("/kitchen-sinks").
     * По умолчанию navigate добавляет новое место назначения в задний стек.
     * Вы можете изменить поведение navigate, добавив к navigate() вызову дополнительные параметры навигации:
     * https://developer.android.com/jetpack/compose/navigation#nav-to-composable
     * Перейти на начальный экран: navController.popBackStack(NavPoint.Home.name, inclusive = false).
     * Чтобы фактически вернуться к предыдущему экрану: navController.navigateUp().
     * Примечание: функция composable() внутри NavHost() является функцией расширения NavGraphBuilder.
     */
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentFeatureNavRoute: String? =
        Regex("^[^/_]*").find(backStackEntry?.destination?.route.toString())?.value
    // Log.d(PROJECT_LOG_TAG, "currentFeatureNavRoute: $currentFeatureNavRoute")
    val currentFeatureFirstNavParameter: String =
        Regex("\\{").replaceFirst(
            input = Regex("\\{[^\\}]*").find(backStackEntry?.destination?.route.toString())?.value
                ?: "",
            replacement = "",
        )
    // Log.d(PROJECT_LOG_TAG, "currentFeatureFirstNavArgKey: $currentFeatureFirstNavParameter")
    val currentFeatureFirstNavArg: String? =
        backStackEntry?.arguments?.getString(currentFeatureFirstNavParameter)
    // Log.d(PROJECT_LOG_TAG, "currentFeatureFirstNavArg: $currentFeatureFirstNavArg")

    val appLayoutScope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val openDrawer = fun() { appLayoutScope.launch { drawerState.open() } }
    val closeDrawer = fun() { appLayoutScope.launch { drawerState.close() } }
    BackHandler(
        enabled = drawerState.isOpen,
        onBack = closeDrawer
    )

    // val snackbarHostState = remember { SnackbarHostState() }

    // val oiohouh = ImageVector.vectorResource()

    ModalNavigationDrawer(
        // modifier = if (drawerState.isClosed) Modifier.width(0.dp) else Modifier.fillMaxWidth(),
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        scrimColor = Color(0x7F000000),
        drawerContent = {
            navDrawerContent(
                navController,
                currentFeatureNavRoute,
                currentFeatureFirstNavArg,
                closeDrawer,
                // Modifier,
            )
        },
    ) {
        // val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        // val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        //    state = rememberTopAppBarState(),
        //    snapAnimationModel = spring(stiffness = Spring.StiffnessHigh)
        //)
        Scaffold(
            topBar = {
                topBarContent(
                    navController,
                    currentFeatureNavRoute,
                    openDrawer,
                    // Modifier,
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = LocalSnackbarHostState.current)
            },
        ) {
            // Text(
            //     text = "Hello",
            //     modifier = Modifier
            //         .fillMaxSize()
            //         .padding(it)
            // )
            mainContent(
                navController,
                Modifier
                    .fillMaxSize()
                    .padding(it)
            )
        }
    }
}