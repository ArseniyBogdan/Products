package com.example.products.entities.DTO

data class LentaPageOfProductsDTO (
    val products: List<LentaProductDTO>,
    val isLastPage: Boolean
)