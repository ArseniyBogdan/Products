package com.example.products.presentation.epoxy

import android.content.Context
import com.airbnb.epoxy.TypedEpoxyController
import com.example.products.entities.DTO.LentaProductDTO
import java.util.UUID

class ProductLentaEpoxyController(
    val context: Context
): TypedEpoxyController<List<LentaProductDTO>>(){

    private var cData: List<LentaProductDTO>? = null
    override fun buildModels(data: List<LentaProductDTO>?) {
        cData = data

        if(data.isNullOrEmpty()){
            repeat(4){
                ProductLentaEpoxyModel(
                    lentaProduct = null,
                    context = context
                ).id(UUID.randomUUID().toString()).addTo(this)
            }
        }else{
            data.forEach { product ->
                ProductLentaEpoxyModel(
                    lentaProduct = product,
                    context = context
                ).id(product.id).addTo(this)
            }
        }
    }

}