package dev.falkow.blanco.shared.options

sealed interface IActionState {

    data class InProgress(val message: String? = null) : IActionState

    data class Success(val message: String? = null) : IActionState

    data class Error(val message: String? = null) : IActionState
}