package com.example.products.entities.dto

/**
 *
 * Страница товара
 * для отображения в ленте
 *
 * @date 10.03.2024
 * @author ArseniyBogdan
 */

data class LentaPageOfProductsDTO (
    val products: List<LentaProductDTO>,
    val isLastPage: Boolean
)