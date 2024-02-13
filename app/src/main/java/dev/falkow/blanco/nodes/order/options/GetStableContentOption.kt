package dev.falkow.blanco.nodes.order.options

import dev.falkow.blanco.nodes.order.types.StableContent
import dev.falkow.blanco.nodes.order.storages.IOrderRemoteStorage
import javax.inject.Inject

internal class GetStableContentOption @Inject constructor(
    private val devRemoteStorage: IOrderRemoteStorage,
) {

    suspend operator fun invoke(orderNavId: String, queryString: String?): StableContent {
        return devRemoteStorage.getOrderStableContent(orderNavId, queryString)
    }
}