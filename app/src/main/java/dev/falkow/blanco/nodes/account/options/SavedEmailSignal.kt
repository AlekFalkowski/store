package dev.falkow.blanco.nodes.account.options

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavedEmailSignal @Inject constructor(
    private val repository: AccountRepository
) {

    val value: Flow<String?> =
        repository.savedEmail
}