package com.example.products.memmory

import com.example.products.entities.response.ProductResponse

object ProductStorage {
    val products = mutableMapOf<Int, ProductResponse>()
}