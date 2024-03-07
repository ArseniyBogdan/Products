package com.example.products.utils

import com.example.products.entities.DTO.LentaProductDTO
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
