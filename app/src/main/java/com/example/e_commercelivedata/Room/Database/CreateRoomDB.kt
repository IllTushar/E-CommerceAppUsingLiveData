package com.example.e_commercelivedata.Room.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.e_commercelivedata.Room.Cart.CartEntity
import com.example.e_commercelivedata.Room.ProductCache.ProductCacheEntity
import com.example.e_commercelivedata.Room.Queries.Queries

@Database(
    entities = [ProductCacheEntity::class, CartEntity::class],
    version = 2,
    exportSchema = false
)
abstract class CreateRoomDB : RoomDatabase() {
    abstract fun queries(): Queries

    companion object {
        @Volatile
        private var INSTANCE: CreateRoomDB? = null

        // Define the migration from version 1 to 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

                // Create the cart table first
                database.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS `cart` (
                `cartId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `productId` INTEGER NOT NULL,
                `cartQuantity` INTEGER NOT NULL,
                FOREIGN KEY(`productId`) REFERENCES `product_cache`(`productId`)
                ON DELETE CASCADE
            )
            """.trimIndent()
                )
                // Add index to cart table for productId foreign key
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_cart_productId` ON `cart` (`productId`)"
                )
            }
        }

        fun getDatabase(context: Context): CreateRoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        CreateRoomDB::class.java,
                        "product_db"
                    )
                        .addMigrations(MIGRATION_1_2)
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }
}