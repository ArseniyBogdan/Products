package com.example.products.services

import com.example.products.entities.response.PageOfProductsResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

class ProductService {

    private val mapper = ObjectMapper().registerModule(KotlinModule())

    fun getPageOfProducts(
        skipCountItems: Int,
        limitCountItems: Int
    ): PageOfProductsResponse{
        val response = HttpService.sendGetRequest(
            requestUrl = "https://dummyjson.com/products?skip=$skipCountItems&limit=$limitCountItems"
        )
        return mapper.readValue(response, PageOfProductsResponse::class.java)

    }
}