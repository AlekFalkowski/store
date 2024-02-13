package dev.falkow.blanco.nodes.catalog_product.display.blocks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import dev.falkow.blanco.nodes.catalog_product.types.ImageGallery
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Gallery(
    modifier: Modifier = Modifier,
    imageGalleryList: List<ImageGallery>,
    galleryPagerState: PagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { imageGalleryList.size }
    ),
    galleryPopupIsShown: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    showButtonsFlag: Boolean
) {
    fun showPopUp() {
        galleryPopupIsShown.value = true
    }

    fun hidePopUp() {
        galleryPopupIsShown.value = false
    }

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(4f / 4f)
    ) {
        when (galleryPopupIsShown.value) {
            false -> {
                HorizontalPager(
                    modifier = Modifier,
                    state = galleryPagerState,
                    pageSpacing = 0.dp,
                    userScrollEnabled = true,
                    outOfBoundsPageCount = 2, // Загружать больше страниц вне экрана.
                    flingBehavior = PagerDefaults.flingBehavior(
                        state = galleryPagerState,
                        pagerSnapDistance = PagerSnapDistance.atMost(1) // Максимальное количество страниц, которые жест пролистывания может прокручивать до одной страницы за раз
                    )
                ) {
                    OutlinedCard(
                        onClick = {
                            showPopUp()
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxHeight(),
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(0.dp),
                            contentColor = MaterialTheme.colorScheme.onSurface,
                        ),
                        border = BorderStroke(
                            width = Dp.Hairline,
                            color = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(imageGalleryList[it].src)
                                .crossfade(true) // Плавная анимация при успешном выполнении запроса.
                                .decoderFactory(SvgDecoder.Factory()) // Отображение svg.
                                .build(),
                            // model = productCard.imageSrc,
                            // placeholder = "Loading",
                            // error = painterResource(CoreR.drawable.ic_broken_image),
                            contentDescription = imageGalleryList[it].description,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .aspectRatio(4f / 3f),
                            contentScale = ContentScale.Fit,
                        )
                        Text(
                            text = imageGalleryList[it].description,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp),
                            shape = RoundedCornerShape(4.dp)
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${galleryPagerState.currentPage + 1} - ${galleryPagerState.pageCount}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                // Row(
                //     Modifier
                //         .height(40.dp)
                //         .fillMaxWidth()
                //         .align(Alignment.BottomCenter)
                //         .padding(bottom = 16.dp),
                //     horizontalArrangement = Arrangement.Center,
                //     verticalAlignment = Alignment.Bottom,
                // ) {
                //     repeat(pageCount) { iteration ->
                //         val color =
                //             if (galleryPagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                //         Box(
                //             modifier = Modifier
                //                 .padding(2.dp)
                //                 .clip(CircleShape)
                //                 .background(color)
                //                 .size(10.dp)
                //         )
                //     }
                // }

                // val coroutineScope = rememberCoroutineScope()
                // Button(
                //     onClick = {
                //         coroutineScope.launch {
                //             // Call scroll to on galleryPagerState
                //             galleryPagerState.animateScrollToPage(5)
                //         }
                //     },
                //     modifier = Modifier.align(Alignment.BottomCenter)
                // ) {
                //     Text("Jump to Page 5")
                // }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val prevButtonVisible = remember {
                        derivedStateOf {
                            galleryPagerState.currentPage > 0
                        }
                    }

                    val nextButtonVisible = remember {
                        derivedStateOf {
                            galleryPagerState.currentPage < galleryPagerState.pageCount - 1
                        }
                    }

                    if (showButtonsFlag) {
                        Button(
                            enabled = prevButtonVisible.value,
                            onClick = {
                                val prevPageIndex = galleryPagerState.currentPage - 1
                                coroutineScope.launch {
                                    galleryPagerState.animateScrollToPage(
                                        prevPageIndex
                                    )
                                }
                            },
                        ) {
                            Text(text = "Prev")
                        }

                        Button(
                            enabled = nextButtonVisible.value,
                            onClick = {
                                val nextPageIndex = galleryPagerState.currentPage + 1
                                coroutineScope.launch {
                                    galleryPagerState.animateScrollToPage(
                                        nextPageIndex
                                    )
                                }
                            },
                        ) {
                            Text(text = "Next")
                        }
                    }
                }
            }

            true -> {
                class GalleryBlockPopupPositionProvider(
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
                        // )
                        return IntOffset(0, 0)
                    }
                }
                Popup(
                    popupPositionProvider = GalleryBlockPopupPositionProvider(),
                    onDismissRequest = {
                        hidePopUp()
                    },
                    properties = PopupProperties(
                        focusable = true,
                        dismissOnBackPress = true,
                        dismissOnClickOutside = false,
                        excludeFromSystemGesture = false,
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            // .background(color = Color(0x7F000000)),
                            .background(color = MaterialTheme.colorScheme.background),
                    ) {
                        HorizontalPager(
                            modifier = Modifier
                                // .background(color = MaterialTheme.colorScheme.onPrimary)
                                .fillMaxSize(),
                            state = galleryPagerState,
                            pageSpacing = 0.dp,
                            userScrollEnabled = true,
                            outOfBoundsPageCount = 2, // Загружать больше страниц вне экрана.
                            flingBehavior = PagerDefaults.flingBehavior(
                                state = galleryPagerState,
                                pagerSnapDistance = PagerSnapDistance.atMost(1) // Максимальное количество страниц, которые жест пролистывания может прокручивать до одной страницы за раз
                            )
                        ) {
                            // Box(
                            //     modifier = Modifier
                            //         .fillMaxSize()
                            // ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context = LocalContext.current)
                                    .data(imageGalleryList[it].src)
                                    .crossfade(true) // Плавная анимация при успешном выполнении запроса.
                                    .decoderFactory(SvgDecoder.Factory()) // Отображение svg.
                                    .build(),
                                // model = productCard.imageSrc,
                                // placeholder = "Loading",
                                // error = painterResource(CoreR.drawable.ic_broken_image),
                                contentDescription = imageGalleryList[it].description,
                                modifier = Modifier
                                    .fillMaxSize(),
                                // .aspectRatio(4f / 3f),
                                contentScale = ContentScale.Fit,
                            )
                            // }
                        }

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 16.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp),
                                    shape = RoundedCornerShape(4.dp)
                                ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "${galleryPagerState.currentPage + 1} - ${galleryPagerState.pageCount}",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }

                        IconButton(
                            modifier = Modifier.align(Alignment.TopEnd),
                            onClick = {
                                hidePopUp()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "Close"
                            )
                        }
                    }
                }
            }
        }
    }

    // val lazyRowState = rememberLazyListState()
    // LazyRow(
    //     state = lazyRowState,
    //     modifier = modifier
    //         .fillMaxWidth()
    //         .aspectRatio(4f / 4f),
    //     // contentPadding = PaddingValues(
    //     //     start = 24.dp,
    //     //     end = 24.dp,
    //     // ),
    //     // horizontalArrangement = Arrangement.spacedBy(8.dp),
    // ) {
    //     // itemsIndexed(galleryImageList) { index, image ->
    //     items(
    //         items = galleryImageList,
    //         key = { it.src },
    //     ) {
    //         OutlinedCard(
    //             // onClick = { onClick() },
    //             modifier = Modifier
    //                 .padding(8.dp)
    //                 .fillMaxHeight()
    //                 .aspectRatio(4f / 4f),
    //             colors = CardDefaults.outlinedCardColors(
    //                 containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(0.dp),
    //                 contentColor = MaterialTheme.colorScheme.onSurface,
    //             ),
    //             border = BorderStroke(
    //                 width = Dp.Hairline,
    //                 color = MaterialTheme.colorScheme.surfaceVariant
    //             )
    //         ) {
    //             AsyncImage(
    //                 model = ImageRequest.Builder(context = LocalContext.current)
    //                     .data(it.src)
    //                     .crossfade(true) // Плавная анимация при успешном выполнении запроса.
    //                     .decoderFactory(SvgDecoder.Factory()) // Отображение svg.
    //                     .build(),
    //                 // model = productCard.imageSrc,
    //                 // placeholder = "Loading",
    //                 // error = painterResource(CoreR.drawable.ic_broken_image),
    //                 contentDescription = it.description,
    //                 modifier = Modifier
    //                     .padding(8.dp)
    //                     .fillMaxWidth()
    //                     .aspectRatio(4f / 3f),
    //                 contentScale = ContentScale.Fit,
    //             )
    //             Text(
    //                 text = it.description,
    //                 modifier = Modifier.padding(8.dp)
    //             )
    //         }
    //     }
    // }
}