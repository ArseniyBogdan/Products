package com.example.products.presentation.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.products.entities.DTO.LentaProductDTO
import com.example.products.usecases.GetPageOfProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {

    private val getPageOfProductsUseCase = GetPageOfProductsUseCase()

    private val _products = MutableLiveData<MutableList<LentaProductDTO>>(mutableListOf())

    val products: LiveData<MutableList<LentaProductDTO>>
        get() = _products

    private var currentPage = 0
    var isLastPage = false
    var isLoading = false

    private val _isRefreshing = MutableLiveData(false)

    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    private val _isFounded = MutableLiveData(true)

    val isFounded: LiveData<Boolean>
        get() = _isFounded

    init {
        download()
    }

    fun download(){
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            val productPage = try{
                getPageOfProductsUseCase.execute(
                    pageSelected = currentPage,
                    pageSize = 20
                )
            }catch (e: Exception){
                Log.d("ProductViewModel.download", "Can't download page")
                if(currentPage == 0){
                    _isFounded.postValue(false)
                }
                null
            }

            productPage?.products?.let {
                _products.value?.addAll(it)
                isLastPage = productPage.isLastPage
                currentPage++
                _isFounded.postValue(true)
            }

            _products.postValue(_products.value)
            isLoading = false
            _isRefreshing.postValue(false)
        }
    }

    fun shouldPaginate() = !isLastPage && !isLoading

    fun refresh(){
        _isRefreshing.value = true
        _isFounded.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            _products.value?.clear()
            currentPage = 0
            download()
        }
    }

}