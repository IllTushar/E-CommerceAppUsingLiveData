package com.example.e_commercelivedata.Room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.e_commercelivedata.Room.ProductCache.ProductCacheEntity

@Entity(
    tableName = "wishlist",
    foreignKeys = [
        ForeignKey(
            entity = ProductCacheEntity::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("productId")]
)
data class WishListEntity(
    @PrimaryKey
    val productId: Int
)
