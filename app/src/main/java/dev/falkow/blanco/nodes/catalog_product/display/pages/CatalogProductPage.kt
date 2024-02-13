package dev.falkow.blanco.nodes.catalog_product.display.pages

import android.text.Html
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import dev.falkow.blanco.nodes.catalog_product.display.blocks.Gallery
import dev.falkow.blanco.nodes.catalog_product.model.states.CatalogProductState
import dev.falkow.blanco.nodes.catalog_product.types.ImageGallery
import dev.falkow.blanco.shared.display.sections.Footer
import dev.falkow.blanco.shared.display.local_providers.LocalWindowWidthClass

@Composable
internal fun CatalogProductPage(
    navigateToHome: () -> Unit,
    catalogNavId: String,
    catalogProductNavId: String,
    catalogProductState: CatalogProductState,
    preferredIsGalleryButtonsDisplayEnable: Boolean,
    setPreferredIsGalleryButtonsDisplayEnable: (showButtonsFlag: Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
    ) {
        when (catalogProductState) {
            is CatalogProductState.Loading ->
                Text(text = "Loading...")

            is CatalogProductState.Error ->
                Text(text = "Error")

            is CatalogProductState.Success -> {
                MainTitleSection(
                    title = catalogProductState.stableContent.title,
                    article = catalogProductState.stableContent.article,
                )
                Button(
                    onClick = { setPreferredIsGalleryButtonsDisplayEnable(!preferredIsGalleryButtonsDisplayEnable) }
                ) {
                    Text(
                        text = if (preferredIsGalleryButtonsDisplayEnable) "Hide buttons" else "Show buttons"
                    )
                }
                CatalogProductSection(
                    imageGalleryList = catalogProductState.stableContent.imageGalleryList,
                    price = catalogProductState.stableContent.price,
                    specification = catalogProductState.stableContent.specification,
                    showButtonsFlag = preferredIsGalleryButtonsDisplayEnable,
                )
                RelatedCatalogProductSection(
                    relatedCatalogProduct = "RelatedCatalogProductSection"
                )
            }
        }

        Footer()
    }
}

@Composable
private fun MainTitleSection(
    title: String = "",
    article: String = "",
) {
    Text(
        style = MaterialTheme.typography.headlineMedium,
        text = buildAnnotatedString {
            append(Html.fromHtml(title, 0))
            withStyle(
                SpanStyle(
                    fontSize = 0.7.em,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                append(" $article")
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CatalogProductSection(
    imageGalleryList: List<ImageGallery>,
    price: String,
    specification: String,
    // description: ,
    showButtonsFlag: Boolean,
) {
    val galleryPagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { imageGalleryList.size }
    )

    val galleryPopupIsShown = rememberSaveable { mutableStateOf(false) }

    val windowWidthClass: WindowWidthSizeClass = LocalWindowWidthClass.current

    when (windowWidthClass) {
        WindowWidthSizeClass.Expanded, // Expanded: Width > 840
        WindowWidthSizeClass.Medium -> { // Medium: 600 < width < 840
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Gallery(
                        imageGalleryList = imageGalleryList,
                        galleryPagerState = galleryPagerState,
                        galleryPopupIsShown = galleryPopupIsShown,
                        showButtonsFlag = showButtonsFlag,
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    PriceBlock(price = price)
                    SpecificationBlock(specification = specification)
                    DescriptionBlock(description = "DescriptionBlock")
                }
            }
        }

        else -> { // Compact: Width < 600
            Gallery(
                imageGalleryList = imageGalleryList,
                galleryPagerState = galleryPagerState,
                galleryPopupIsShown = galleryPopupIsShown,
                showButtonsFlag = showButtonsFlag,
            )
            PriceBlock(price = price)
            SpecificationBlock(specification = specification)
            DescriptionBlock(description = "DescriptionBlock")
        }
    }
}

@Composable
private fun PriceBlock(
    price: String,
) {
    Text(text = "Price: " + Html.fromHtml(price, 0) + "руб.")
}

@Composable
private fun SpecificationBlock(
    specification: String
) {
    Text(text = "Specification: ")
    // Text(text = "" + Html.fromHtml(specification, 0))
    Text(text = specification)
}

@Composable
private fun DescriptionBlock(
    description: String,
) {
    Text(text = description)
}

@Composable
private fun RelatedCatalogProductSection(
    relatedCatalogProduct: String,
) {
    Text(text = relatedCatalogProduct)
}