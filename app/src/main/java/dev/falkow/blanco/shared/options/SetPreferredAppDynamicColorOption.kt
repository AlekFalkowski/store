package dev.falkow.blanco.shared.options

import dev.falkow.blanco.shared.storages.IKeyValueLocalStorage
import dev.falkow.blanco.shared.storages.KeyValueTable
import dev.falkow.blanco.shared.storages.Keys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SetPreferredAppDynamicColorOption @Inject constructor(
    private val keyValueLocalStore: IKeyValueLocalStorage
) {

    private val _state = MutableStateFlow<IActionState?>(null)
    val state: StateFlow<IActionState?> = _state.asStateFlow()

    suspend operator fun invoke(dynamicColor: Boolean): Unit {

        if (state.value is IActionState.InProgress) {
            return
        }

        _state.value = IActionState.InProgress()
        try {
            keyValueLocalStore.setValueByKey(
                KeyValueTable(
                    key = Keys.APP_DYNAMIC_COLOR,
                    value = dynamicColor.toString(),
                )
            )
            _state.value = IActionState.Success()
        } catch (e: Throwable) {
            _state.value = IActionState.Error()
        }
    }
}