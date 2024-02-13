package dev.falkow.blanco.nodes.order.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object OrderProvidingModule {

    // @Provides
    // fun provideOrderStaticContentData(): OrderStaticContentData = OrderStaticContentData
}