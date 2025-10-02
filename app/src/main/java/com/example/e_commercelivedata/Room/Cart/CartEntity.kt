package com.example.e_commercelivedata.Room.Cart

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.e_commercelivedata.Room.ProductCache.ProductCacheEntity

@Entity(
    tableName = "cart",
)
data class CartEntity(
    @PrimaryKey
    val productId: Int, //foreign key
    val cartQuantity: Int
)