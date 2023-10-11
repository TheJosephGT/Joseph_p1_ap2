package com.example.primerparcial.di

import android.content.Context
import androidx.room.Room
import com.example.primerparcial.data.Database
import com.example.primerparcial.data.repository.CounterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn ( SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext appContext: Context) : Database =
        Room.databaseBuilder(
            appContext,
            Database::class.java,
            "Database.db"
        ).fallbackToDestructiveMigration().build()
    @Provides
    fun providePreferences(@ApplicationContext context: Context) = CounterRepository(context)
}