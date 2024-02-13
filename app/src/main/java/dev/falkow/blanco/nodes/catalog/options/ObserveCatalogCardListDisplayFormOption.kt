package dev.falkow.blanco.nodes.catalog.options

import dev.falkow.blanco.nodes.catalog.types.CardListDisplayForms
import dev.falkow.blanco.shared.storages.Keys
import dev.falkow.blanco.shared.storages.IKeyValueLocalStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ObserveCatalogCardListDisplayFormOption @Inject constructor(
    keyValueLocalStore: IKeyValueLocalStorage,
) {

    val value: Flow<CardListDisplayForms?> =
        keyValueLocalStore.observeValueByKey(Keys.CATALOG_CARD_LIST_DISPLAY_FORM)
            .map {
                when (it) {
                    null -> null
                    else -> CardListDisplayForms.valueOf(it)
                }
            }
            .catch {
                emit(null)
                // when (it) {
                //     is IllegalArgumentException -> emit(null)
                //     else -> throw it
                // }
            }
}