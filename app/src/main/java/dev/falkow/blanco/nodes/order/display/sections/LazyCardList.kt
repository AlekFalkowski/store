package dev.falkow.blanco.nodes.order.display.sections

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridScopeMarker
import androidx.compose.foundation.lazy.grid.items
import dev.falkow.blanco.nodes.order.display.blocks.OrderItemCard
import dev.falkow.blanco.nodes.order.types.OrderItem

@LazyGridScopeMarker
internal fun LazyCardList(
    lazyGridScope: LazyGridScope,
    navigateTo: (navId: String) -> Unit,
    navigateToOrderProduct: (orderNavId: String, orderProductNavId: String) -> Unit,
    cardList: List<OrderItem>,
) {
    lazyGridScope.items(
        items = cardList,
        span = {
            GridItemSpan(
                currentLineSpan = maxLineSpan
            )
        },
        contentType = { "ORDER_ITEM_CARD" },
        key = { it.number }
    ) {
        OrderItemCard(
            orderItem = it,
            onClick = {},
            // onClick = {
            //     navigateTo(
            //         it.navId,
            //     )
            // },
        )
    }
}