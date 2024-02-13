package dev.falkow.blanco.nodes.catalog.display.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.falkow.blanco.nodes.catalog.display.sections.LazyCardList
import dev.falkow.blanco.nodes.catalog.display.sections.LazyCardListDisplayFormSelector
import dev.falkow.blanco.nodes.catalog.display.panels.Filter
import dev.falkow.blanco.nodes.catalog.model.states.CardListDisplayFormState
import dev.falkow.blanco.nodes.catalog.model.states.DynamicContentState
import dev.falkow.blanco.nodes.catalog.model.states.StableContentState
import dev.falkow.blanco.nodes.catalog.types.CardListDisplayForms
import dev.falkow.blanco.shared.display.local_providers.LocalSnackbarHostState
import dev.falkow.blanco.shared.display.templates.BasicPageTemplate
import dev.falkow.blanco.shared.display.sections.LazyFooter
import dev.falkow.blanco.shared.display.sections.LazyTitle

internal enum class CatalogPageContentTypes {
    PAGE_STATUS,
    TITLE_SECTION,
    CATALOG_LIST_DISPLAY_FORM_BUTTON,
    ACTIONS_ROW,
    CATALOG_ITEM_CARD,
    PRODUCT_GROUP_BUTTON,
    FOOTER,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CatalogPage(
    navigateTo: (navId: String) -> Unit,
    navigateToHome: () -> Unit,
    navigateToCatalog: (catalogNavId: String) -> Unit,
    navigateToCatalogProduct: (catalogNavId: String, catalogProductNavId: String) -> Unit,
    doStartInitialization: () -> Unit,
    stableContentState: StableContentState?,
    uriPrefix: String,
    queryString: String,
    cardListDisplayFormState: CardListDisplayFormState,
    setCardListDisplayForm: (CardListDisplayForms) -> Unit,
    textFieldsStates: Map<String, MutableState<String>>,
    singleChoiceFieldsStates: Map<String, MutableState<String>>,
    multiChoiceFieldsStates: Map<String, MutableState<Set<String>>>,
    dynamicContentState: DynamicContentState?,
    getDynamicContent: () -> Unit,
    resetFieldStates: () -> Unit,
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

        val snackbarHostState: SnackbarHostState = LocalSnackbarHostState.current

        if (cardListDisplayFormState is CardListDisplayFormState.ErrorSettingNewValue) {
            LaunchedEffect(snackbarHostState) {
                val result = snackbarHostState
                    .showSnackbar(
                        message = cardListDisplayFormState.errorMessage ?: "Error",
                        // actionLabel = "Action",
                        withDismissAction = true,
                        duration = SnackbarDuration.Long
                    )
                // when (result) {
                //     SnackbarResult.ActionPerformed -> {
                //         /* Handle snackbar action performed */
                //     }
                //     SnackbarResult.Dismissed -> {
                //         /* Handle snackbar dismissed */
                //     }
                // }
                cardListDisplayFormState.resetError?.let { it() }
            }
        }

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
                                title = staticContent.title
                            )

                            item(
                                span = { GridItemSpan(maxLineSpan) },
                                contentType = "Link",
                                key = "Link"
                            ) {
                                Text(
                                    text = "$uriPrefix?$queryString",
                                    fontSize = 6.sp
                                )
                            }

                            LazyCardListDisplayFormSelector(
                                lazyGridScope = this,
                                cardListDisplayFormState = cardListDisplayFormState,
                                setCardListDisplayForm = setCardListDisplayForm,
                            )

                            LazyCardList(
                                lazyGridScope = this,
                                navigateTo = navigateTo,
                                navigateToHome = navigateToHome,
                                navigateToCatalog = navigateToCatalog,
                                navigateToCatalogProduct = navigateToCatalogProduct,
                                dynamicContentState = dynamicContentState,
                                cardListDisplayFormState = cardListDisplayFormState,
                            )

                            LazyFooter(
                                lazyGridScope = this,
                            )
                        }
                    },
                    thirdColumnContent = { modifier ->
                        Filter(
                            modifier = modifier,
                            filterConfig = staticContent.filterConfig,
                            textFieldsStates = textFieldsStates,
                            singleChoiceFieldsStates = singleChoiceFieldsStates,
                            multiChoiceFieldsStates = multiChoiceFieldsStates,
                            getDynamicContent = getDynamicContent,
                            resetFieldStates = resetFieldStates,
                        )
                    },
                    thirdColumnButtonIcon = Icons.Outlined.Tune,
                    thirdColumnButtonText = "Filter",
                    thirdColumnTitle = "Filter",
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