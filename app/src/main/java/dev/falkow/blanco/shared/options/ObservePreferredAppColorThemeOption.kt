package dev.falkow.blanco.shared.options

import dev.falkow.blanco.shared.config.ColorThemes
import dev.falkow.blanco.shared.storages.IKeyValueLocalStorage
import dev.falkow.blanco.shared.storages.Keys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObservePreferredAppColorThemeOption @Inject constructor(
    keyValueLocalStore: IKeyValueLocalStorage
) {

    val value: Flow<ColorThemes?> =
        keyValueLocalStore.observeValueByKey(Keys.APP_COLOR_THEME)
            .map {
                when (it) {
                    null -> null
                    else -> ColorThemes.valueOf(it)
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