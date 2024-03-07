package com.example.products.entities.response

data class ProductResponse(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>,
)

// {"id":1,
// "title":"iPhone 9",
// "description":"An apple mobile which is nothing like apple",
// "price":549,
// "discountPercentage":12.96,
// "rating":4.69,
// "stock":94,
// "brand":"Apple",
// "category":"smartphones",
// "thumbnail":"https://cdn.dummyjson.com/product-images/1/thumbnail.jpg",
// "images":["https://cdn.dummyjson.com/product-images/1/1.jpg",
// "https://cdn.dummyjson.com/product-images/1/2.jpg",
// "https://cdn.dummyjson.com/product-images/1/3.jpg",
// "https://cdn.dummyjson.com/product-images/1/4.jpg",
// "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg"]}