package com.example.e_commercelivedata.Room.ProductCache

import android.app.Application
import com.example.e_commercelivedata.Room.Database.CreateRoomDB
import com.example.e_commercelivedata.Room.Queries.Queries
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
    fun provideProductQueries(db: CreateRoomDB): Queries {
        return db.queries()
    }
}