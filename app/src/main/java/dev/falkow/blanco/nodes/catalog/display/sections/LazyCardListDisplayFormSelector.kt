package dev.falkow.blanco.nodes.catalog.display.sections

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridScopeMarker
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.falkow.blanco.nodes.catalog.display.pages.CatalogPageContentTypes
import dev.falkow.blanco.nodes.catalog.model.states.CardListDisplayFormState
import dev.falkow.blanco.nodes.catalog.types.CardListDisplayForms

@LazyGridScopeMarker
internal fun LazyCardListDisplayFormSelector(
    lazyGridScope: LazyGridScope,
    cardListDisplayFormState: CardListDisplayFormState,
    setCardListDisplayForm: (CardListDisplayForms) -> Unit,
) {
    lazyGridScope.item(
        span = { GridItemSpan(maxLineSpan) },
        contentType = CatalogPageContentTypes.CATALOG_LIST_DISPLAY_FORM_BUTTON,
        key = "CatalogCardListDisplayFormButton"
    ) {
        Box() {
            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = {
                    setCardListDisplayForm(
                        when (cardListDisplayFormState.form) {
                            CardListDisplayForms.GRID -> CardListDisplayForms.LIST
                            CardListDisplayForms.LIST -> CardListDisplayForms.GRID
                        }
                    )
                },
            ) {
                when (cardListDisplayFormState) {
                    is CardListDisplayFormState.Calm,
                    is CardListDisplayFormState.ErrorSettingNewValue -> {
                        Icon(
                            imageVector =
                            when (cardListDisplayFormState.form) {
                                CardListDisplayForms.GRID -> Icons.Outlined.ViewAgenda
                                CardListDisplayForms.LIST -> Icons.Outlined.GridView
                            },
                            contentDescription = "Catalog display form",
                        )
                    }

                    is CardListDisplayFormState.TryingSetNewValue -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            strokeWidth = 3.dp
                        )
                    }
                }
            }
        }
    }
}