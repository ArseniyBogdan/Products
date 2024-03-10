package com.example.products.utils

import com.example.products.entities.response.PageOfProductsResponse
import com.example.products.memory.ProductStorage

object ProductStorageUtil {

    fun saveProductResponses(pageOfProductsResponse: PageOfProductsResponse){
        pageOfProductsResponse.products.forEach { product ->
            ProductStorage.products[product.id] = product
        }
    }
}