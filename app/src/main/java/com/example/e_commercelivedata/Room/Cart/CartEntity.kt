package com.example.e_commercelivedata.Room.Cart

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.e_commercelivedata.Room.ProductCache.ProductCacheEntity

@Entity(
    tableName = "cart",
    foreignKeys = [
        ForeignKey(
            entity = ProductCacheEntity::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [Index("productId")]
)
data class CartEntity(
    @PrimaryKey
    val productId: Int, //foreign key
    val cartQuantity: Int
)