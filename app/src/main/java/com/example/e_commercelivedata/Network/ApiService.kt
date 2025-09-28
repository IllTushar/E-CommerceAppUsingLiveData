package com.example.e_commercelivedata.Network

import com.example.e_commercelivedata.HomePage.data.ProductResponseClass

import retrofit2.http.*

interface ApiService {

    @GET("products")
    suspend fun getAllProducts(): List<ProductResponseClass>
}