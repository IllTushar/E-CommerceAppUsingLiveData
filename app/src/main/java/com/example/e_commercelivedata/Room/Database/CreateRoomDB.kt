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
import com.example.e_commercelivedata.Room.WishListEntity

@Database(
    entities = [ProductCacheEntity::class, CartEntity::class, WishListEntity::class],
    version = 3,
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
                `productId` INTEGER NOT NULL,
                `cartQuantity` INTEGER NOT NULL,
                FOREIGN KEY(`productId`) REFERENCES `product_cache`(`productId`)
                ON DELETE NO ACTION
            )
            """.trimIndent()
                )
                // Add index to cart table for productId foreign key
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_cart_productId` ON `cart` (`productId`)"
                )
            }
        }
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS `wishlist` (
                `productId` INTEGER NOT NULL,
                PRIMARY KEY(`productId`),
                FOREIGN KEY(`productId`) REFERENCES `product_cache`(`productId`) ON DELETE NO ACTION
            )
            """.trimIndent()
                )

                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_wishlist_productId` ON `wishlist` (`productId`)"
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
                        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }
}