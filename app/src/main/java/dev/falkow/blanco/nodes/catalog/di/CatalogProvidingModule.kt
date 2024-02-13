package dev.falkow.blanco.nodes.catalog.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object CatalogProvidingModule {

    // @Provides
    // fun provideCatalogStaticContentData(): CatalogStaticContentData = CatalogStaticContentData
}