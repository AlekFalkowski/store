package dev.falkow.blanco.shared.options

import dev.falkow.blanco.shared.types.AccountData
import kotlinx.coroutines.flow.Flow

interface IAccountRepository {

    val accountData: Flow<AccountData?>
}