package com.example.e_commercelivedata.ApiRepo

import com.example.e_commercelivedata.HomePage.data.ProductResponseClass
import com.example.e_commercelivedata.Network.ApiService
import javax.inject.Inject

class Repository @Inject constructor(val apiService: ApiService) {
    suspend fun repoCall(): List<ProductResponseClass> {
        return apiService.getAllProducts()
    }
}