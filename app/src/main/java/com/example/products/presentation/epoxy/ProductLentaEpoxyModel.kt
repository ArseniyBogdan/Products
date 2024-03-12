package com.example.products.presentation.epoxy

import android.content.Context
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.products.R
import com.example.products.databinding.ProductListItemBinding
import com.example.products.entities.DTO.LentaProductDTO
import com.example.products.utils.ViewBindingKotlinModel
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
            productDescription.text = product.description
            productDescription.visibility = View.GONE
            setDownIcon()

            setupExpandClickListener()

            Glide.with(context).load(lentaProduct.thumbnail)
                .diskCacheStrategy(DiskCacheStrategy.DATA).into(productThumbnail)

        } ?: shimmer.startShimmer()
    }

    private fun ProductListItemBinding.setupExpandClickListener(){
        dropDownLayout.setOnClickListener {
            TransitionManager.beginDelayedTransition(lentaProductLayout, AutoTransition())

            productDescription.visibility = when(productDescription.visibility){
                View.GONE -> {
                    setUpIcon()
                    View.VISIBLE
                }
                View.VISIBLE -> {
                    setDownIcon()
                    View.GONE
                }
                else -> {
                    setDownIcon()
                    View.GONE
                }
            }
        }
    }

    private fun ProductListItemBinding.setUpIcon(){
        dropDownIc.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_arrow_drop_up_24))
    }
    private fun ProductListItemBinding.setDownIcon(){
        dropDownIc.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_arrow_drop_down_24))
    }
}