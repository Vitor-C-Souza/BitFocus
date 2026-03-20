package br.me.vitorcsouza.bitfocus.di

import br.me.vitorcsouza.bitfocus.data.local.datasource.focus.FocusDataSource
import br.me.vitorcsouza.bitfocus.data.local.datasource.focus.FocusDataSourceImpl
import br.me.vitorcsouza.bitfocus.data.repository.FocusRepositoryImpl
import br.me.vitorcsouza.bitfocus.domain.repository.FocusRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindFocusDataSource(
        impl: FocusDataSourceImpl
    ): FocusDataSource

    @Binds
    @Singleton
    abstract fun bindFocusRepository(
        impl: FocusRepositoryImpl
    ): FocusRepository
}
