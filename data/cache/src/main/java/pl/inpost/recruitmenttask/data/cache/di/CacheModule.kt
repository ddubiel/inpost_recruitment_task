package pl.inpost.recruitmenttask.data.cache.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.cache.InpostDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class CacheModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = InpostDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideShipmentDao(database: InpostDatabase) = database.shipmentDao

}