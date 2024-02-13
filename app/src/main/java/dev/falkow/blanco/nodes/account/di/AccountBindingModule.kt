package dev.falkow.blanco.nodes.account.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.falkow.blanco.nodes.account.options.AccountRepository
import dev.falkow.blanco.shared.options.IAccountRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class AccountBindingModule {

    @Binds
    abstract fun bindAccountDataRepository(
        repository: AccountRepository
    ): IAccountRepository

}