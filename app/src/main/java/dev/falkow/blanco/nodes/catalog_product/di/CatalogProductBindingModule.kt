package dev.falkow.blanco.nodes.catalog_product.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CatalogProductBindingModule {

    // @Binds
    // abstract fun bindCatalogProductContentRepositorySpec(
    //     repository: CatalogProductContentRepository
    // ): CatalogProductContentRepositorySpec
}