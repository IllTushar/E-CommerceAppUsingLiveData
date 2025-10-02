package com.example.e_commercelivedata.Room.ProductCache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "product_cache")
data class ProductCacheEntity(
    @PrimaryKey
    val productId: Int = 0,
    val title: String = "",
    val price: Float = 0.0F,
    val description: String = "",
    val category: String = "",
    val imageURI: String = "",
    val ratingCount: Float = 0.0F,
    val totalQuantity: Int = 0,
)
