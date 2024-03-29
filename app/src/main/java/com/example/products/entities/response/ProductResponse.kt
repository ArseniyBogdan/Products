package com.example.products.entities.response

/**
 *
 * Товар
 *
 * @date 7.03.2024
 * @author ArseniyBogdan
 */

data class ProductResponse(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>,
)