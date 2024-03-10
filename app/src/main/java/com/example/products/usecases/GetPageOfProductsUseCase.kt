package com.example.products.usecases

import android.util.Log
import com.example.products.entities.DTO.LentaPageOfProductsDTO
import com.example.products.entities.DTO.LentaProductDTO
import com.example.products.services.ProductService
import com.example.products.utils.ProductStorageUtil
import com.example.products.utils.toLentaProductDTO

class GetPageOfProductsUseCase {

    private val productService = ProductService()

    fun execute(pageSelected: Int = 0, pageSize: Int = 20): LentaPageOfProductsDTO {
        Log.d("Executing GetPageOfProductsUseCase", "pageSelected=$pageSelected and pageSize=$pageSize")
        val pageOfProducts = productService.getPageOfProducts(
            skipCountItems = pageSelected * pageSize,
            limitCountItems = pageSize
        )
        ProductStorageUtil.saveProductResponses(pageOfProducts)

        val productList = pageOfProducts.products.map { it.toLentaProductDTO() }
        return LentaPageOfProductsDTO(
            products = productList,
            isLastPage = pageOfProducts.total <= pageOfProducts.limit + pageOfProducts.skip
        )
    }
}