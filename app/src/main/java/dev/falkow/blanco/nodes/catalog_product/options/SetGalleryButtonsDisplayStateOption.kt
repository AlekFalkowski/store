package dev.falkow.blanco.nodes.catalog_product.options

import dev.falkow.blanco.shared.storages.Keys
import dev.falkow.blanco.shared.storages.IKeyValueLocalStorage
import dev.falkow.blanco.shared.storages.KeyValueTable
import javax.inject.Inject

class SetGalleryButtonsDisplayStateOption @Inject constructor(
    private val keyValueLocalStore: IKeyValueLocalStorage,
) {

    suspend operator fun invoke(isGalleryButtonsDisplayEnable: Boolean): Unit {
        keyValueLocalStore.setValueByKey(
            KeyValueTable(
                key = Keys.GALLERY_BUTTONS_DISPLAY_STATE,
                value = isGalleryButtonsDisplayEnable.toString(),
            )
        )
    }
}