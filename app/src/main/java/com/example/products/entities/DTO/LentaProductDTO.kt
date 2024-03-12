package com.example.products.entities.DTO

/**
 *
 * Сущность товара для
 * отображения в ленте
 *
 * @date 7.03.2024
 * @author ArseniyBogdan
 */
data class LentaProductDTO(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String
)
