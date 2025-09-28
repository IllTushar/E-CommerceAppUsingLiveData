package com.example.e_commercelivedata.Room.ProductInfo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductCacheTable::class], version = 1, exportSchema = false)
abstract class CreateRoomDB : RoomDatabase() {
    abstract fun ProductQueries(): ProductQueries

    companion object {
        @Volatile
        private var INSTANCE: CreateRoomDB? = null
        fun getDatabase(context: Context): CreateRoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        CreateRoomDB::class.java,
                        "product_db"
                    ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}