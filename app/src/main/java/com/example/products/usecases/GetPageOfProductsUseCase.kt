package com.example.products.usecases

import android.util.Log
import com.example.products.entities.DTO.LentaProductDTO
import com.example.products.services.ProductService
import com.example.products.utils.ProductStorageUtil
import com.example.products.utils.toLentaProductDTO

class GetPageOfProductsUseCase {

    private val productService = ProductService()

    fun execute(pageSelected: Int = 0, pageSize: Int = 20): List<LentaProductDTO> {
        Log.d("Executing GetPageOfProductsUseCase", "pageSelected=$pageSelected and pageSize=$pageSize")
        val pageOfProducts = productService.getPageOfProducts(
            skipCountItems = pageSelected * pageSize,
            limitCountItems = pageSize
        )
        ProductStorageUtil.saveProductResponses(pageOfProducts)

        return pageOfProducts.products.map { it.toLentaProductDTO() }
    }
}