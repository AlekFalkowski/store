package dev.falkow.blanco.layout.display.panels

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import dev.falkow.blanco.shared.config.Features
import dev.falkow.blanco.shared.types.NavDrawerGroup

@Composable
fun AppNavDrawer(
    navController: NavHostController,
    currentFeatureNavRoute: String?,
    currentFeatureFirstNavArg: String?,
    closeDrawer: () -> Unit,
    navDrawerContentIsLoading: Boolean,
    navDrawerContentResult: Result<List<NavDrawerGroup>>?,
) {
    ModalDrawerSheet(
        modifier = Modifier.width(300.dp),
        drawerTonalElevation = 1.dp, // or 0.dp
        drawerShape = RoundedCornerShape(
            topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp
        ),
    ) {
        Row(
            modifier = Modifier
                .height(63.dp)
                .fillMaxWidth()
                .padding(start = 28.dp, end = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Blanco.Moscow",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )
            IconButton(
                onClick = closeDrawer
            ) {
                Icon(imageVector = Icons.Outlined.Close, contentDescription = "Close")
            }
        }
        HorizontalDivider(
            thickness = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 4.dp)
        ) {

            if (navDrawerContentIsLoading || navDrawerContentResult == null) {
                // Box(modifier = Modifier.fillMaxSize()) {
                //     Text(
                //         text = "Loading...",
                //         modifier = Modifier.align(Alignment.Center)
                //     )
                // }
            } else {
                navDrawerContentResult.onSuccess { navDrawerContent ->
                    navDrawerContent.forEachIndexed { index, navGroup ->
                        if (index > 0) {
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.padding(/*horizontal = 24.dp,*/ vertical = 8.dp)
                            )
                        }
                        if (navGroup.title != null) {
                            Text(
                                text = navGroup.title!!,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.titleSmall,
                                lineHeight = 48.sp,
                                modifier = Modifier.padding(horizontal = 28.dp)
                            )
                        } else {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        navGroup.linkList.forEach { navLink ->
                            val targetFeatureNavRoute: String =
                                Regex("^[^/_]*").find(navLink.navDestination)?.value?.uppercase() ?: ""
                            val targetFeatureFirstNavArg: String =
                                Regex("/").replaceFirst(
                                    input = Regex("/[^/]*").find(navLink.navDestination)?.value
                                        ?: "",
                                    replacement = ""
                                )
                            val selected: Boolean = when (targetFeatureNavRoute) {
                                Features.CATALOG,
                                Features.CATALOG_PRODUCT -> targetFeatureFirstNavArg == currentFeatureFirstNavArg

                                else -> targetFeatureNavRoute == currentFeatureNavRoute
                            }
                            // Log.d(PROJECT_LOG_TAG, "targetFeatureNavRoute: $targetFeatureNavRoute")
                            // Log.d(PROJECT_LOG_TAG, "currentFeatureFirstNavArg: $currentFeatureFirstNavArg")
                            // Log.d(PROJECT_LOG_TAG, "targetFeatureFirstNavArg: $targetFeatureFirstNavArg")
                            // Log.d(
                            //     PROJECT_LOG_TAG,
                            //     "val_selected: ${targetFeatureFirstNavArg == currentFeatureFirstNavArg}"
                            // )

                            NavigationDrawerItem(
                                icon = {
                                    // Log.d(TAG, navLink.iconSrc ?: "null")
                                    if (navLink.iconSrc != null) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(context = LocalContext.current)
                                                .data(navLink.iconSrc)
                                                .crossfade(true) // Плавная анимация при успешном выполнении запроса.
                                                .decoderFactory(SvgDecoder.Factory()) // Отображение svg.
                                                .build(),
                                            // model = productCard.imageSrc,
                                            // placeholder = "Loading",
                                            // error = painterResource(CoreR.drawable.ic_broken_image),
                                            contentDescription = null,
                                            modifier = Modifier
                                                // .clip(CircleShape)
                                                .size(20.dp),
                                            contentScale = ContentScale.Fit,
                                            colorFilter = ColorFilter.tint(
                                                if (selected) {
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                } else {
                                                    MaterialTheme.colorScheme.onSurfaceVariant
                                                }
                                            )
                                        )
                                    }
                                },
                                label = { Text(text = navLink.title) },
                                selected = selected,
                                onClick = {
                                    closeDrawer()
                                    try {
                                        navController.popBackStack(Features.HOME, false)
                                        navController.navigate(navLink.navDestination)
                                    } catch (_: Exception) {
                                    }
                                },
                                modifier = Modifier
                                    // .padding(NavigationDrawerItemDefaults.ItemPadding)
                                    .padding(horizontal = 12.dp)
                                    .height(48.dp)
                            )
                        }
                    }
                }

                navDrawerContentResult.onFailure {
                    // Box(modifier = Modifier.fillMaxSize()) {
                    //     Text(
                    //         text = "Loading error \uD83D\uDE41",
                    //         modifier = Modifier.align(Alignment.Center)
                    //     )
                    // }
                }
            }
        }
    }
}