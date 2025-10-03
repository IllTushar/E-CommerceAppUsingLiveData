package com.example.e_commercelivedata.Room.Queries

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_commercelivedata.Room.Cart.CartEntity

import com.example.e_commercelivedata.Room.ProductCache.ProductCacheEntity
import com.example.e_commercelivedata.Room.WishListEntity

@Dao
interface Queries {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProduct(productCacheTable: List<ProductCacheEntity>)

    @Query("DELETE FROM product_cache")
    suspend fun deleteAllProductCache()

    @Query("SELECT * FROM product_cache")
    fun getAllUser(): LiveData<List<ProductCacheEntity>>

    @Query("SELECT * FROM product_cache WHERE category =:category")
    fun getCategoryProduct(category: String): LiveData<List<ProductCacheEntity>>

    // Cart
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItems(cartEntity: CartEntity)

    @Query("DELETE FROM  cart WHERE productId = :productId")
    suspend fun deleteProduct(productId: Int)

    @Query("SELECT * FROM cart")
    fun getProductsInCart(): List<CartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoWishList(wishlistItem: WishListEntity)

}