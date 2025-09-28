package com.example.e_commercelivedata.Room.ProductInfo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_cache")
data class ProductCacheTable(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val imageURI: String,
    val ratingCount: Float,
    val totalQuantity: Int
)
