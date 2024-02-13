package dev.falkow.blanco.shared.display.sections

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridScopeMarker

@LazyGridScopeMarker
fun LazyTitle(
    lazyGridScope: LazyGridScope,
    title: String
) {
    lazyGridScope.item(
        span = { GridItemSpan(maxLineSpan) },
        contentType = "LazyTitle",
        key = "LazyTitle"
    ) {
        Title(title = title)
    }
}