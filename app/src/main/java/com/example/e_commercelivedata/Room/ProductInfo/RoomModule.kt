package com.example.e_commercelivedata.Room.ProductInfo

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): CreateRoomDB {
        return CreateRoomDB.getDatabase(app)
    }

    @Provides
    @Singleton
    fun provideProductQueries(db: CreateRoomDB): ProductQueries {
        return db.ProductQueries()
    }
}