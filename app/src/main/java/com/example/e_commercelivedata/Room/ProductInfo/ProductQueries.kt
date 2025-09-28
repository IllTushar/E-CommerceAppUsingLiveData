package com.example.e_commercelivedata.Room.ProductInfo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductQueries {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProduct(productCacheTable: List<ProductCacheTable>)

    @Query("DELETE FROM product_cache")
    suspend fun deleteAllProductCache()

    @Query("SELECT * FROM product_cache")
    fun getAllUser(): LiveData<List<ProductCacheTable>>

    @Query("SELECT * FROM product_cache WHERE category =:category")
    fun getCategoryProduct(category: String): LiveData<List<ProductCacheTable>>
}