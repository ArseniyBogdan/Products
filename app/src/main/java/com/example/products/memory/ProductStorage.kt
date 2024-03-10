package com.example.products.memory

import com.example.products.entities.response.ProductResponse

object ProductStorage {
    val products = mutableMapOf<Int, ProductResponse>()
}