package dev.falkow.blanco.nodes.catalog.options

import dev.falkow.blanco.shared.storages.Keys
import dev.falkow.blanco.shared.storages.IKeyValueLocalStorage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ObserveQueryStringOption @Inject constructor(
    private val keyValueLocalStore: IKeyValueLocalStorage
) {

    operator fun invoke(catalogNavId: String): Flow<String?> {
        return keyValueLocalStore.observeValueByKey(Keys.CATALOG_QUERY_STRING + catalogNavId)
    }
    // suspend operator fun invoke(catalogNavId: String): String? {
    //     return keyValueLocalStore.getValueByKey(Keys.CATALOG_STATE_STRING + catalogNavId)
    // }
}