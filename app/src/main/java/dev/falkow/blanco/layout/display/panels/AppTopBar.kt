package dev.falkow.blanco.layout.display.panels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.falkow.blanco.nodes.account.display.navigation.navigateToAccount
import dev.falkow.blanco.shared.config.Features

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    navController: NavHostController,
    currentFeatureNavRoute: String?,
    onNavButtonClick: () -> Unit,
) {
    TopAppBar(
        // modifier = Modifier.height(56.dp),
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
            // scrolledContainerColor = MaterialTheme.colorScheme.applyTonalElevation(
            //     backgroundColor = containerColor,
            //     elevation = TopAppBarSmallTokens.OnScrollContainerElevation
            // ),
            // navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            // titleContentColor = MaterialTheme.colorScheme.onSurface,
            // actionIconContentColor = MaterialTheme.colorScheme.onSurface
            // containerColor = MaterialTheme.colorScheme.surfaceVariant,
            // navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            // titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            // actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            // containerColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.inversePrimary,
            // containerColor = MaterialTheme.colorScheme.inversePrimary,
            // navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            // titleContentColor = MaterialTheme.colorScheme.onSurface,
            // actionIconContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        // scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(
                onClick = { onNavButtonClick() },
                // modifier = Modifier.padding(top = 4.dp)
            ) {
                Icon(Icons.Outlined.Menu, "Main menu")
            }
        },
        title = {
            Text(
                text = "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        actions = {
            TopBarIconButton(
                onClick = { /* navController.navigateToSearch() */ },
                targetFeatureNavRoute = "search", // Feature.SEARCH.name,
                currentFeatureNavRoute = currentFeatureNavRoute,
                contentDescription = "Search",
                // filledIcon = ImageVector.vectorResource(id = R.drawable.whatsapp),
                filledIcon = Icons.Filled.Search,
                outlinedIcon = Icons.Outlined.Search,
            )
            TopBarIconButton(
                onClick = { /* navController.navigateToBookmark() */ },
                targetFeatureNavRoute = "bookmarks", // Feature.BOOKMARKS.name,
                currentFeatureNavRoute = currentFeatureNavRoute,
                contentDescription = "Bookmarks",
                filledIcon = Icons.Filled.Bookmarks,
                outlinedIcon = Icons.Outlined.Bookmarks,
            )
            TopBarIconButton(
                onClick = { /* navController.navigateToCart() */ },
                targetFeatureNavRoute = "cart", // Feature.CART.name,
                currentFeatureNavRoute = currentFeatureNavRoute,
                contentDescription = "Cart",
                filledIcon = Icons.Filled.ShoppingCart,
                outlinedIcon = Icons.Outlined.ShoppingCart,
            )
            TopBarIconButton(
                onClick = { navController.navigateToAccount() },
                targetFeatureNavRoute = Features.ACCOUNT,
                currentFeatureNavRoute = currentFeatureNavRoute,
                contentDescription = "Account",
                filledIcon = Icons.Filled.Person,
                outlinedIcon = Icons.Outlined.Person,
            )
        },
    )
}

@Composable
private fun TopBarIconButton(
    onClick: (Boolean) -> Unit,
    targetFeatureNavRoute: String,
    currentFeatureNavRoute: String?,
    contentDescription: String,
    filledIcon: ImageVector,
    outlinedIcon: ImageVector,
) {
    val targetFeatureIsActive = targetFeatureNavRoute == currentFeatureNavRoute

    IconToggleButton(
        checked = targetFeatureIsActive,
        onCheckedChange = onClick,
        colors = IconButtonDefaults.iconToggleButtonColors(
            checkedContainerColor = if (targetFeatureIsActive) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                IconButtonDefaults.iconToggleButtonColors().checkedContentColor
            },
        ),
        // modifier = Modifier.padding(top = 4.dp),
    ) {
        if (targetFeatureIsActive) {
            Icon(contentDescription = contentDescription, imageVector = filledIcon)
        } else {
            Icon(contentDescription = contentDescription, imageVector = outlinedIcon)
        }
    }
}