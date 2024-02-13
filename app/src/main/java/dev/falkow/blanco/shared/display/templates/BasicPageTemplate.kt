package dev.falkow.blanco.shared.display.templates

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import dev.falkow.blanco.shared.display.local_providers.LocalWindowWidthClass

@Composable
fun BasicPageTemplate(
    firstColumnContent: @Composable ((modifier: Modifier) -> Unit)? = null,
    secondColumnContent: @Composable ((modifier: Modifier, contentMaxWidth: Int) -> Unit)? = null,
    thirdColumnButtonIcon: ImageVector? = null,
    thirdColumnButtonText: String? = null,
    thirdColumnTitle: String? = null,
    thirdColumnContent: @Composable ((modifier: Modifier) -> Unit)? = null,
) {

    val windowWidthClass: WindowWidthSizeClass = LocalWindowWidthClass.current

    val sidePadding: Int = when (windowWidthClass) {
        WindowWidthSizeClass.Expanded -> 32
        WindowWidthSizeClass.Medium -> 16
        else -> 8
    }

    val contentMaxWidth: Int =
        if (firstColumnContent == null && thirdColumnContent == null) 1500 else 780


    val movableSecondColumnContent = secondColumnContent?.let {
        remember { movableContentOf(secondColumnContent) }
    }
    val movableThirdColumnContent = thirdColumnContent?.let {
        remember { movableContentOf(thirdColumnContent) }
    }

    when (windowWidthClass) {
        WindowWidthSizeClass.Expanded, // Expanded: Width >= 840
        WindowWidthSizeClass.Medium -> { // Medium: 600 <= Width < 840
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                movableSecondColumnContent?.invoke(Modifier.weight(1f), contentMaxWidth)
                thirdColumnContent?.invoke(Modifier.width(300.dp))
            }
        }

        else -> { // Compact: Width < 600
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                movableSecondColumnContent?.invoke(Modifier.fillMaxSize(), contentMaxWidth)
                movableThirdColumnContent?.let {
                    EndSideDrawer(
                        thirdColumnButtonIcon,
                        thirdColumnButtonText,
                        thirdColumnTitle,
                        movableThirdColumnContent,
                    )
                }
            }
        }
    }
}

@Composable
private fun EndSideDrawer(
    drawerButtonIcon: ImageVector?,
    drawerButtonText: String?,
    drawerTitle: String?,
    drawerContent: @Composable (modifier: Modifier) -> Unit,
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        var popupIsShown by remember { mutableStateOf(false) }
        fun showPopUp() {
            popupIsShown = true
        }

        fun hidePopUp() {
            popupIsShown = false
        }

        class EndSidePopupPositionProvider(
            private val x: Int = 0, private val y: Int = 0
        ) : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize
            ): IntOffset {
                // return IntOffset(
                //    windowSize.width - popupContentSize.width, 0
                //)
                return IntOffset(0, 0)
            }
        }

        SmallFloatingActionButton(
            onClick = { showPopUp() },
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .padding(end = 16.dp, top = 56.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(
                imageVector = drawerButtonIcon ?: Icons.Outlined.Menu,
                contentDescription = drawerButtonText ?: "Menu",
            )
        }

        if (popupIsShown) {
            Popup(
                popupPositionProvider = EndSidePopupPositionProvider(),
                onDismissRequest = { hidePopUp() },
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                    excludeFromSystemGesture = true,
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(0x7F000000)),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .clickable(enabled = true, onClick = { hidePopUp() })
                    )
                    Column(
                        modifier = Modifier
                            .width(300.dp)
                            .fillMaxHeight()
                            .background(
                                color = MaterialTheme.colorScheme.surfaceColorAtElevation(
                                    1.dp // or 0.dp
                                )
                            ),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(63.dp)
                                .padding(start = 24.dp, end = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = drawerTitle ?: "",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            IconButton(
                                onClick = { hidePopUp() }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "Close"
                                )
                            }
                        }
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.surfaceVariant
                        )
                        drawerContent(Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}