package dev.falkow.blanco.shared.display.sections

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridScopeMarker

@LazyGridScopeMarker
fun LazyFooter(
    lazyGridScope: LazyGridScope,
) {
    lazyGridScope.item(
        span = { GridItemSpan(maxLineSpan) },
        contentType = "LazyFooter",
        key = "LazyFooter"
    ) {
        Footer()
    }
}