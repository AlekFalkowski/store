package dev.falkow.blanco.shared.options

import dev.falkow.blanco.shared.config.ColorSchemes
import dev.falkow.blanco.shared.storages.IKeyValueLocalStorage
import dev.falkow.blanco.shared.storages.Keys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObservePreferredAppColorSchemeOption @Inject constructor(
    keyValueLocalStorage: IKeyValueLocalStorage,
) {

    val value: Flow<ColorSchemes?> =
        keyValueLocalStorage.observeValueByKey(Keys.APP_COLOR_SCHEME)
            .map {
                when (it) {
                    null -> null
                    else -> ColorSchemes.valueOf(it)
                }
            }
            .catch {
                when (it) {
                    is IllegalArgumentException -> emit(null)
                    else -> throw it
                }
            }
            .catch { emit(null) }
}