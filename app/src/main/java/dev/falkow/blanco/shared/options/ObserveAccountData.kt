package dev.falkow.blanco.shared.options

import dev.falkow.blanco.shared.types.AccountData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObserveAccountData @Inject constructor(
    repository: IAccountRepository,
) {

    val value: Flow<AccountData?> =
        repository.accountData
}