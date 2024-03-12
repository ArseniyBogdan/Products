package com.example.products.usecases

import android.util.Log
import com.example.products.entities.dto.LentaPageOfProductsDTO
import com.example.products.services.ProductService
import com.example.products.utils.toLentaPageOfProductsDTO

class GetPageOfProductsUseCase {

    private val productService = ProductService()

    fun execute(pageSelected: Int = 0, pageSize: Int = 20): LentaPageOfProductsDTO {
        Log.d("Executing GetPageOfProductsUseCase", "pageSelected=$pageSelected and pageSize=$pageSize")

        return productService.getPageOfProducts(
            skipCountItems = pageSelected * pageSize,
            limitCountItems = pageSize
        ).toLentaPageOfProductsDTO()
    }
}