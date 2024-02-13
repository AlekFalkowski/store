package dev.falkow.blanco.nodes.catalog_product.options

import dev.falkow.blanco.shared.storages.Keys
import dev.falkow.blanco.shared.storages.IKeyValueLocalStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveGalleryButtonsDisplayStateOption @Inject constructor(
    keyValueLocalStorage: IKeyValueLocalStorage,
) {

    val value: Flow<Boolean?> =
        keyValueLocalStorage.observeValueByKey(Keys.GALLERY_BUTTONS_DISPLAY_STATE)
            .map {
                when (it) {
                    "true" -> true
                    "false" -> false
                    else -> null
                }
            }
}