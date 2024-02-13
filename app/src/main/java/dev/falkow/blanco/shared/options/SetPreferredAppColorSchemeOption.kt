package dev.falkow.blanco.shared.options

import dev.falkow.blanco.shared.config.ColorSchemes
import dev.falkow.blanco.shared.storages.IKeyValueLocalStorage
import dev.falkow.blanco.shared.storages.KeyValueTable
import dev.falkow.blanco.shared.storages.Keys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SetPreferredAppColorSchemeOption @Inject constructor(
    private val keyValueLocalStorage: IKeyValueLocalStorage,
) {

    private val _state = MutableStateFlow<IActionState?>(null)
    val state: StateFlow<IActionState?> = _state.asStateFlow()

    suspend operator fun invoke(colorScheme: ColorSchemes): Unit {

        if (state.value is IActionState.InProgress) {
            return
        }

        _state.value = IActionState.InProgress()
        try {
            keyValueLocalStorage.setValueByKey(
                KeyValueTable(
                    key = Keys.APP_COLOR_SCHEME,
                    value = colorScheme.name,
                )
            )
            _state.value = IActionState.Success()
        } catch (e: Throwable) {
            _state.value = IActionState.Error()
        }
    }
}