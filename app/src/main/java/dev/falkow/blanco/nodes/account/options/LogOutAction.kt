package dev.falkow.blanco.nodes.account.options

import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class LogOutAction @Inject constructor(
    private val repository: AccountRepository,
) {

    private val _state = MutableStateFlow<LogOutActionState>(LogOutActionState.ReadyToStart)
    val state: StateFlow<LogOutActionState> = _state.asStateFlow()

    fun resetError(): Unit {
        if (state.value is LogOutActionState.InProgress) {
            return
        }
        _state.value = LogOutActionState.ReadyToStart
    }

    suspend operator fun invoke(): Unit {

        if (state.value is LogOutActionState.InProgress) {
            return
        }

        _state.value = LogOutActionState.InProgress
        try {
            repository.logOutAccount()
            _state.value = LogOutActionState.ReadyToStart
        } catch (e: Throwable) {
            _state.value = LogOutActionState.Error()
            TODO("Unexpected exception!")
        }
    }
}