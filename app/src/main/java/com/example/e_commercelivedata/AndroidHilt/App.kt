package com.example.e_commercelivedata.AndroidHilt

import android.app.Application
import com.example.e_commercelivedata.ApiRepo.Repository
import com.example.e_commercelivedata.HomePage.data.ProductResponseClass

import com.example.e_commercelivedata.Room.ProductCache.ProductCacheEntity
import com.example.e_commercelivedata.Room.Queries.Queries


import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var dao: Queries

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val products = repository.productCacheRepo()
                insertDataInRoomDb(products)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun insertDataInRoomDb(products: List<ProductResponseClass>) {
        dao.deleteAllProductCache()
        val response: List<ProductCacheEntity> = products.map { product ->
            ProductCacheEntity(
                productId = product.id,
                title = product.title,
                description = product.description,
                price = product.price,
                category = product.category,
                imageURI = product.image,
                ratingCount = product.rating.rate,
                totalQuantity = product.rating.count,
            )
        }
        dao.insertAllProduct(response)
    }
}
