package dev.falkow.blanco.shared.options

import dev.falkow.blanco.shared.storages.IKeyValueLocalStorage
import dev.falkow.blanco.shared.storages.Keys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObservePreferredAppDynamicColorOption @Inject constructor(
    keyValueLocalStore: IKeyValueLocalStorage
) {

    val value: Flow<Boolean?> =
        keyValueLocalStore.observeValueByKey(Keys.APP_DYNAMIC_COLOR)
            .map {
                when (it) {
                    "true" -> true
                    "false" -> false
                    else -> null
                }
            }
            .catch { emit(null) }
}