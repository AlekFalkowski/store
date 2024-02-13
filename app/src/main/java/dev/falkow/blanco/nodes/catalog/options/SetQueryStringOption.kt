package dev.falkow.blanco.nodes.catalog.options

import dev.falkow.blanco.shared.storages.Keys
import dev.falkow.blanco.shared.storages.IKeyValueLocalStorage
import dev.falkow.blanco.shared.storages.KeyValueTable
import javax.inject.Inject

internal class SetQueryStringOption @Inject constructor(
    private val keyValueLocalStore: IKeyValueLocalStorage
) {

    suspend operator fun invoke(catalogNavId: String, queryString: String): Unit {
        keyValueLocalStore.setValueByKey(
            KeyValueTable(
                key = Keys.CATALOG_QUERY_STRING + catalogNavId,
                value = queryString,
            )
        )
    }
}