package com.example.products.entities.response

data class PageOfProductsResponse(
    val products: List<ProductResponse>,
    val total: Int,
    val skip: Int,
    val limit: Int
)