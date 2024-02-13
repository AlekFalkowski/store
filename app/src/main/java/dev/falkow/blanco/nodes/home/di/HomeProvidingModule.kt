package dev.falkow.blanco.nodes.home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.falkow.blanco.nodes.home.resources.HomeContentLocalResource

@Module
@InstallIn(ViewModelComponent::class)
object HomeProvidingModule {

    @ViewModelScoped
    @Provides
    fun provideHomeContentLocalResource(): HomeContentLocalResource = HomeContentLocalResource
}