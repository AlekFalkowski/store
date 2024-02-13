package dev.falkow.blanco.nodes.home.display.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.falkow.blanco.shared.display.blocks.CatalogCard
import dev.falkow.blanco.nodes.home.model.states.StableContentState
import dev.falkow.blanco.shared.display.templates.BasicPageTemplate
import dev.falkow.blanco.shared.display.sections.LazyFooter

private object ScreenContentType {
    const val PAGE_TITLE = "PAGE_TITLE"
    const val PRODUCT_GROUP_TITLE = "PRODUCT_GROUP_TITLE"
    const val PRODUCT_GROUP_STATUS = "PRODUCT_GROUP_STATUS"
    const val PRODUCT_GROUP_CARD = "PRODUCT_GROUP_CARD"
    const val CATALOG_ITEM_CARD = "CATALOG_ITEM_CARD"
    const val PRODUCT_GROUP_BUTTON = "PRODUCT_GROUP_BUTTON"
    const val FOOTER = "FOOTER"
}

@Composable
internal fun HomePage(
    stableContentState: StableContentState,
    navigateToCatalog: (catalogNavId: String) -> Unit,
    // navigateToCatalogProduct: (catalogProductNavId: String) -> Unit,
    navigateToOrder: (orderNavId: String) -> Unit,
) {

    val lazyGridState =
        rememberLazyGridState() // Запоминает положение прокрутки при повороте экрана.

    BasicPageTemplate(
        secondColumnContent = { modifier, contentMaxWidth ->
            LazyVerticalGrid(
                modifier = modifier,
                // state = lazyGridState,
                columns = GridCells.Adaptive(minSize = 152.dp),
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item(
                    span = { GridItemSpan(maxLineSpan) },
                    contentType = ScreenContentType.PAGE_TITLE,
                    key = "page-title"
                ) {
                    PageTitle(title = "Магазин Blanco в Москве")
                }

                item(
                    span = { GridItemSpan(maxLineSpan) },
                    contentType = "navigateToOrder",
                    key = "navigateToOrder",
                ) {
                    Button(
                        onClick = { navigateToOrder("211921") }
                    ) {
                        Text(text = "Navigate To Order")
                    }
                }

                item(
                    span = { GridItemSpan(maxLineSpan) },
                    contentType = ScreenContentType.PRODUCT_GROUP_TITLE,
                    key = "2"
                ) {
                    ProductGroupTitle(title = "Серии кухонных моек Blanco")
                }
                when (stableContentState) {
                    is StableContentState.Loading -> item(
                        span = { GridItemSpan(maxLineSpan) },
                        contentType = ScreenContentType.PRODUCT_GROUP_STATUS,
                        key = "loading"
                    ) {
                        Text(text = "Loading...")
                    }

                    is StableContentState.Error -> item(
                        span = { GridItemSpan(maxLineSpan) },
                        contentType = ScreenContentType.PRODUCT_GROUP_STATUS,
                        key = "error"
                    ) {
                        Text(text = "Error")
                    }

                    is StableContentState.Success -> items(
                        stableContentState.stableContent.kitchenSinksCardList,
                        // span = { GridItemSpan(maxLineSpan) },
                        contentType = { ScreenContentType.PRODUCT_GROUP_CARD },
                        key = { it.imageResId }
                    ) {
                        CatalogCard(
                            catalogListCard = it,
                            onClick = { navigateToCatalog(it.catalogNavId) }
                        )
                    }
                }
                item(
                    span = { GridItemSpan(maxLineSpan) },
                    contentType = ScreenContentType.PRODUCT_GROUP_BUTTON,
                    key = "3"
                ) {
                    ProductGroupButton(
                        label = "Все серии",
                        onClick = { navigateToCatalog("kitchen-sinks") }
                    )
                }

                item(
                    span = { GridItemSpan(maxLineSpan) },
                    contentType = ScreenContentType.PRODUCT_GROUP_TITLE,
                    key = "4"
                ) {
                    ProductGroupTitle(title = "Смесители Blanco")
                }
                item(
                    span = { GridItemSpan(maxLineSpan) },
                    contentType = ScreenContentType.PRODUCT_GROUP_BUTTON,
                    key = "5"
                ) {
                    ProductGroupButton(
                        label = "Перейти в раздел",
                        onClick = { navigateToCatalog("kitchen-taps") },
                    )
                }

                item(
                    span = { GridItemSpan(maxLineSpan) },
                    contentType = ScreenContentType.PRODUCT_GROUP_TITLE,
                    key = "6"
                ) {
                    ProductGroupTitle(title = "Дозаторы для моющего средства Blanco")
                }
                item(
                    span = { GridItemSpan(maxLineSpan) },
                    contentType = ScreenContentType.PRODUCT_GROUP_BUTTON,
                    key = "7"
                ) {
                    ProductGroupButton(
                        label = "Перейти в раздел",
                        onClick = { navigateToCatalog("soap-dispensers") },
                    )
                }

                item(
                    span = { GridItemSpan(maxLineSpan) },
                    contentType = ScreenContentType.PRODUCT_GROUP_TITLE,
                    key = "8"
                ) {
                    ProductGroupTitle(title = "Мусорные системы Blanco")
                }
                item(
                    span = { GridItemSpan(maxLineSpan) },
                    contentType = ScreenContentType.PRODUCT_GROUP_BUTTON,
                    key = "9"
                ) {
                    ProductGroupButton(
                        label = "Перейти в раздел",
                        onClick = { navigateToCatalog("waste-systems") },
                    )
                }

                item(
                    span = { GridItemSpan(maxLineSpan) },
                    contentType = ScreenContentType.PRODUCT_GROUP_TITLE,
                    key = "10"
                ) {
                    ProductGroupTitle(title = "Аксессуары Blanco")
                }
                item(
                    span = { GridItemSpan(maxLineSpan) },
                    contentType = ScreenContentType.PRODUCT_GROUP_BUTTON,
                    key = "11"
                ) {
                    ProductGroupButton(
                        label = "Перейти в раздел",
                        onClick = { navigateToCatalog("accessories") },
                    )
                }

                item(
                    span = { GridItemSpan(maxLineSpan) },
                    contentType = ScreenContentType.PRODUCT_GROUP_TITLE,
                    key = "12"
                ) {
                    ProductGroupTitle(title = "Измельчители пищевых отходов для моек Blanco")
                }
                item(
                    span = { GridItemSpan(maxLineSpan) },
                    contentType = ScreenContentType.PRODUCT_GROUP_BUTTON,
                    key = "13"
                ) {
                    ProductGroupButton(
                        label = "Перейти в раздел",
                        onClick = { navigateToCatalog("disposers") },
                    )
                }

                // item(
                //     span = { GridItemSpan(maxLineSpan) },
                //     contentType = ScreenContentType.PRODUCT_GROUP_TITLE,
                //     key = "14"
                // ) {
                //     ProductGroupTitle(title = "Краны для питьевой воды")
                // }
                // item(
                //     span = { GridItemSpan(maxLineSpan) },
                //     contentType = ScreenContentType.PRODUCT_GROUP_BUTTON,
                //     key = "15"
                // ) {
                //     ProductGroupButton(
                //         label = "Перейти в раздел",
                //         onClick = { navigateToCatalog("water-drinking-faucets") },
                //     )
                // }

                LazyFooter(
                    lazyGridScope = this,
                )
            }
        }
    )
}

@Composable
private fun PageTitle(
    title: String
) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun ProductGroupTitle(
    title: String
) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            )
            .wrapContentHeight(align = Alignment.CenterVertically)
            .padding(horizontal = 8.dp),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun ProductGroupButton(
    label: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.padding(vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        FilledTonalButton(onClick = { onClick() }) {
            Text(text = label)
        }
    }
}