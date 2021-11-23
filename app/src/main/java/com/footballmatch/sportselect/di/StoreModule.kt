package com.footballmatch.sportselect.di

import android.content.Context
import com.footballmatch.sportselect.store.PreferenceStore
import com.footballmatch.sportselect.store.PreferenceStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StoreModule {

    @Provides
    @Singleton
    fun providePreferenceStore(
        @ApplicationContext context: Context
    ): PreferenceStore = PreferenceStoreImpl(context)
}