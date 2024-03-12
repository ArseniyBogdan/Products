package com.example.products.entities.response

/**
 *
 * Страница товаров.
 * Ответ сервера на запрос
 * постраничной загрузки.
 *
 * @date 7.03.2024
 * @author ArseniyBogdan
 */


data class PageOfProductsResponse(
    val products: List<ProductResponse>,
    val total: Int,
    val skip: Int,
    val limit: Int
)