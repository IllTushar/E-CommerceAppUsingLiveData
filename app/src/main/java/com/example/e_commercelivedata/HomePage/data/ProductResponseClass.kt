package com.example.e_commercelivedata.HomePage.data

data class ProductResponseClass(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Float,
    val image: String,
    val rating: Rating
)

data class Rating(
    val count: Int,
    val rate: Float
)
