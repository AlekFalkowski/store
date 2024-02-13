package dev.falkow.blanco.nodes.catalog.options

import dev.falkow.blanco.nodes.catalog.types.StableContent
import dev.falkow.blanco.nodes.catalog.storages.ICatalogDevRemoteStorage
import javax.inject.Inject

internal class GetStableContentOption @Inject constructor(
    private val devRemoteStorage: ICatalogDevRemoteStorage,
) {

    suspend operator fun invoke(catalogNavId: String, queryString: String): StableContent {
        return devRemoteStorage.getCatalogStableContent(catalogNavId, queryString)
    }
}