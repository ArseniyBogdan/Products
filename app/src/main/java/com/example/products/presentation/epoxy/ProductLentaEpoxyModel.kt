package com.example.products.presentation.epoxy

import android.content.Context
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.products.R
import com.example.products.databinding.ProductListItemBinding
import com.example.products.entities.DTO.LentaProductDTO
import com.example.products.utils.ViewBindingKotlinModel
import java.net.URI
import kotlin.math.roundToInt

class ProductLentaEpoxyModel(
    val context: Context,
    val lentaProduct: LentaProductDTO?
): ViewBindingKotlinModel<ProductListItemBinding>(R.layout.product_list_item) {

    override fun ProductListItemBinding.bind() {
        lentaProductCard.isVisible = lentaProduct != null
        shimmer.isVisible = lentaProduct == null

        lentaProduct?.let { product ->
            shimmer.stopShimmer()
            val priceText = product.price.toString() + " $"
            val discountText = "-" + product.discountPercentage.roundToInt().toString() + "%"
            val priceWithDiscountText = (product.price * (1 - product.discountPercentage / 100))
                .roundToInt().toString() + " $"
            val stockText = "Осталось " + product.stock.toString() + " шт"

            productTitle.text = product.title
            productPrice.text = priceText
            productDiscount.text = discountText
            productStock.text = stockText
            productRating.text = String.format("%.1f", product.rating)
            productPriceWithDiscount.text = priceWithDiscountText

            Glide.with(context).load(lentaProduct.thumbnail)
                .diskCacheStrategy(DiskCacheStrategy.DATA).into(productThumbnail)
        } ?: shimmer.startShimmer()
    }
}