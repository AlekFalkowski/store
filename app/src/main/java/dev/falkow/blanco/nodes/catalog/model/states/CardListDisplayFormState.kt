package dev.falkow.blanco.nodes.catalog.model.states

import dev.falkow.blanco.nodes.catalog.types.CardListDisplayForms

internal sealed interface CardListDisplayFormState {

    val form: CardListDisplayForms

    data class Calm(
        override val form: CardListDisplayForms
    ) : CardListDisplayFormState

    data class TryingSetNewValue(
        override val form: CardListDisplayForms,
    ) : CardListDisplayFormState

    data class ErrorSettingNewValue(
        override val form: CardListDisplayForms,
        val errorMessage: String? = null,
        val resetError: (() -> Unit)? = null,
    ) : CardListDisplayFormState
}