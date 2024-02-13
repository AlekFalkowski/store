package dev.falkow.blanco.nodes.catalog.display.sections

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridScopeMarker
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.falkow.blanco.nodes.catalog.display.pages.CatalogPageContentTypes
import dev.falkow.blanco.nodes.catalog.model.states.CardListDisplayFormState
import dev.falkow.blanco.nodes.catalog.model.states.DynamicContentState
import dev.falkow.blanco.nodes.catalog.types.CardListDisplayForms
import dev.falkow.blanco.shared.display.blocks.AdvertisingCard
import dev.falkow.blanco.shared.display.blocks.CatalogCard
import dev.falkow.blanco.shared.display.blocks.CustomizableProductCard
import dev.falkow.blanco.shared.display.blocks.FinishedProductCard

@LazyGridScopeMarker
internal fun LazyCardList(
    lazyGridScope: LazyGridScope,
    navigateTo: (navId: String) -> Unit,
    navigateToHome: () -> Unit,
    navigateToCatalog: (catalogNavId: String) -> Unit,
    navigateToCatalogProduct: (catalogNavId: String, catalogProductNavId: String) -> Unit,
    dynamicContentState: DynamicContentState?,
    cardListDisplayFormState: CardListDisplayFormState,
) {

    when (dynamicContentState) {
        is DynamicContentState.Loading, null -> {
            lazyGridScope.item(
                span = { GridItemSpan(maxLineSpan) },
                contentType = CatalogPageContentTypes.PAGE_STATUS,
                key = "loading"
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Loading...",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        is DynamicContentState.Success -> {
            lazyGridScope.items(
                items = dynamicContentState.success.cardList,
                span = {
                    GridItemSpan(
                        currentLineSpan =
                        if (cardListDisplayFormState.form == CardListDisplayForms.GRID) 1
                        else maxLineSpan
                    )
                },
                contentType = { CatalogPageContentTypes.CATALOG_ITEM_CARD },
                key = {
                    when (it) {
                        is dev.falkow.blanco.shared.types.AdvertisingCard -> it.navId
                        is dev.falkow.blanco.shared.types.CatalogListCard -> it.catalogNavId
                        is dev.falkow.blanco.shared.types.FinishedProductCard -> it.catalogNavId + it.catalogProductNavId
                        is dev.falkow.blanco.shared.types.CustomizableProductCard -> it.catalogNavId + it.catalogProductNavId
                    }
                }
            ) {
                when (it) {
                    is dev.falkow.blanco.shared.types.AdvertisingCard -> AdvertisingCard(
                        advertisingCard = it,
                        onClick = {
                            navigateTo(
                                it.navId,
                            )
                        },
                    )

                    is dev.falkow.blanco.shared.types.CatalogListCard -> CatalogCard(
                        catalogListCard = it,
                        onClick = {
                            navigateToCatalog(
                                it.catalogNavId,
                            )
                        },
                    )

                    is dev.falkow.blanco.shared.types.FinishedProductCard -> FinishedProductCard(
                        finishedProductCard = it,
                        onClick = {
                            navigateToCatalogProduct(
                                it.catalogNavId,
                                it.catalogProductNavId
                            )
                        },
                    )

                    is dev.falkow.blanco.shared.types.CustomizableProductCard -> CustomizableProductCard(
                        customizableProductCard = it,
                        onClick = {
                            navigateToCatalogProduct(
                                it.catalogNavId,
                                it.catalogProductNavId
                            )
                        },
                    )
                }
            }
        }

        is DynamicContentState.Error -> {
            lazyGridScope.item(
                span = { GridItemSpan(maxLineSpan) },
                contentType = CatalogPageContentTypes.PAGE_STATUS,
                key = "error"
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Loading error \uD83D\uDE41",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}