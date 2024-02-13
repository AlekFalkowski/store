package dev.falkow.blanco.nodes.catalog.options

import dev.falkow.blanco.nodes.catalog.types.CardListDisplayForms
import dev.falkow.blanco.shared.storages.Keys
import dev.falkow.blanco.shared.storages.IKeyValueLocalStorage
import dev.falkow.blanco.shared.storages.KeyValueTable
import javax.inject.Inject

internal class SetCatalogCardListDisplayFormOption @Inject constructor(
    private val keyValueLocalStorage: IKeyValueLocalStorage,
) {

    suspend operator fun invoke(catalogCardListDisplayForm: CardListDisplayForms): Unit {
        keyValueLocalStorage.setValueByKey(
            KeyValueTable(
                key = Keys.CATALOG_CARD_LIST_DISPLAY_FORM,
                value = catalogCardListDisplayForm.name,
            )
        )
    }
}