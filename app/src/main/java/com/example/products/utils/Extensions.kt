package com.example.products.utils

import com.example.products.entities.DTO.LentaPageOfProductsDTO
import com.example.products.entities.DTO.LentaProductDTO
import com.example.products.entities.response.PageOfProductsResponse
import com.example.products.entities.response.ProductResponse

internal fun ProductResponse.toLentaProductDTO() =
    LentaProductDTO(
        id = id,
        title = title,
        description = description,
        price = price,
        discountPercentage = discountPercentage,
        rating = rating,
        stock = stock,
        brand = brand,
        category = category,
        thumbnail = thumbnail
    )

internal fun PageOfProductsResponse.toLentaPageOfProductsDTO() =
    LentaPageOfProductsDTO(
        products = products.map { it.toLentaProductDTO() },
        isLastPage = total <= limit + skip
    )
