package com.example.products.usecases

import android.util.Log
import com.example.products.entities.DTO.LentaPageOfProductsDTO
import com.example.products.services.ProductService
import com.example.products.utils.toLentaPageOfProductsDTO

class GetPageOfProductsByKeyUseCase {
    private val productService = ProductService()

    fun execute(searchKey: String, pageSelected: Int = 0, pageSize: Int = 20): LentaPageOfProductsDTO {
        Log.d("Executing GetPageOfProductsUseCase", "pageSelected=$pageSelected and pageSize=$pageSize")

        return productService.getPageOfProductsByKey(
            searchKey = searchKey,
            skipCountItems = pageSelected * pageSize,
            limitCountItems = pageSize
        ).toLentaPageOfProductsDTO()
    }
}