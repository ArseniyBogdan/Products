package com.example.products.usecases

import android.util.Log
import com.example.products.entities.DTO.LentaPageOfProductsDTO
import com.example.products.services.ProductService
import com.example.products.utils.ProductStorageUtil
import com.example.products.utils.toLentaProductDTO

class GetPageOfProductsByKeyUseCase {
    private val productService = ProductService()

    fun execute(searchKey: String, pageSelected: Int = 0, pageSize: Int = 20): LentaPageOfProductsDTO {
        Log.d("Executing GetPageOfProductsUseCase", "pageSelected=$pageSelected and pageSize=$pageSize")
        val pageOfProducts = productService.getPageOfProductsByKey(
            searchKey = searchKey,
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