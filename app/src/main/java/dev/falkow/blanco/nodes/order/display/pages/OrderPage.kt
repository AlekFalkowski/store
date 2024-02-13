package dev.falkow.blanco.nodes.order.display.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import dev.falkow.blanco.nodes.order.display.sections.LazyCardList
import dev.falkow.blanco.nodes.order.model.states.StableContentState
import dev.falkow.blanco.shared.display.templates.BasicPageTemplate
import dev.falkow.blanco.shared.display.sections.LazyFooter
import dev.falkow.blanco.shared.display.sections.LazyTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderPage(
    navigateTo: (navId: String) -> Unit,
    navigateToHome: () -> Unit,
    navigateToOrderProduct: (orderNavId: String, orderProductNavId: String) -> Unit,
    doStartInitialization: () -> Unit,
    stableContentState: StableContentState?,
) {

    val state = rememberPullToRefreshState()
    if (state.isRefreshing) {
        LaunchedEffect(Unit) {
            // delay(1500)
            doStartInitialization()
            state.endRefresh()
        }
    }
    Box(
        modifier = Modifier.nestedScroll(connection = state.nestedScrollConnection)
    ) {

        when (stableContentState) {
            is StableContentState.Loading, null -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Loading...",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            is StableContentState.Success -> {

                val staticContent = stableContentState.success

                BasicPageTemplate(
                    secondColumnContent = { modifier, contentMaxWidth ->
                        LazyVerticalGrid(
                            modifier = modifier,
                            columns = GridCells.Adaptive(minSize = 152.dp),
                            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {

                            LazyTitle(
                                lazyGridScope = this,
                                title = "Order № ${staticContent.number}"
                            )

                            LazyCardList(
                                lazyGridScope = this,
                                navigateTo = navigateTo,
                                navigateToOrderProduct = navigateToOrderProduct,
                                cardList = staticContent.items
                            )

                            LazyFooter(
                                lazyGridScope = this,
                            )
                        }
                    },
                )
            }

            is StableContentState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
                ) {
                    Text(
                        text = "Loading error \uD83D\uDE41"
                    )
                    Button(
                        onClick = { doStartInitialization() }
                    ) {
                        Text(
                            text = "Try again"
                        )
                    }
                }
            }
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = state,
        )
    }
}