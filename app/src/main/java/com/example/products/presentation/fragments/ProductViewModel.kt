package com.example.products.presentation.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.products.entities.DTO.LentaPageOfProductsDTO
import com.example.products.entities.DTO.LentaProductDTO
import com.example.products.entities.response.PageOfProductsResponse
import com.example.products.usecases.GetPageOfProductsByKeyUseCase
import com.example.products.usecases.GetPageOfProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {

    private val getPageOfProductsUseCase = GetPageOfProductsUseCase()
    private val getPageOfProductsByKeyUseCase = GetPageOfProductsByKeyUseCase()

    private val _products = MutableLiveData<MutableList<LentaProductDTO>>(mutableListOf())

    val products: LiveData<MutableList<LentaProductDTO>>
        get() = _products

    private var currentPage = 0
    private var isLastPage = false
    private var isLoading = false

    private val _isRefreshing = MutableLiveData(false)

    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    private val _isFounded = MutableLiveData(true)

    val isFounded: LiveData<Boolean>
        get() = _isFounded

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val pageOfProducts = downloadNewPage(
                pageSelected = 0
            )
            addNewPage(pageOfProducts)
        }
    }


    fun shouldPaginate() = !isLastPage && !isLoading

    fun refresh(searchKey: String){
        Log.d("searchKey", searchKey)
        isLoading = true
        _isRefreshing.value = true
        _isFounded.postValue(true)
        _products.value = mutableListOf()
        viewModelScope.coroutineContext.cancelChildren()
        viewModelScope.launch(Dispatchers.IO) {
            val pageOfProducts = downloadNewPage(
                searchKey = searchKey,
                pageSelected = 0
            )
            currentPage = 1
            setNewPage(pageOfProducts)
            _isRefreshing.postValue(false)
            isLoading = false
        }
    }

    fun search(searchKey: String){
        Log.d("search", searchKey)
        isLoading = true
        _isFounded.postValue(true)
        _products.value = mutableListOf()
        viewModelScope.coroutineContext.cancelChildren()
        viewModelScope.launch(Dispatchers.IO) {
            val pageOfProducts = downloadNewPage(
                searchKey = searchKey,
                pageSelected = 0
            )
            currentPage = 1
            setNewPage(pageOfProducts)
            isLoading = false
        }
    }

    private fun downloadNewPage(
        searchKey: String = "",
        pageSelected: Int,
        pageCount: Int = 20
    ): LentaPageOfProductsDTO? {
        return try {
            if (searchKey.isEmpty()){
                getPageOfProductsUseCase.execute(
                    pageSelected = pageSelected,
                    pageSize = pageCount
                )
            }
            else{
                getPageOfProductsByKeyUseCase.execute(
                    searchKey = searchKey,
                    pageSelected = pageSelected,
                    pageSize = pageCount
                )
            }
        }catch (e: Exception){
            Log.e("ProductViewModel.download", "Can't download page. message: ${e.message}")
            null
        }
    }

    private suspend fun addNewPage(productPage: LentaPageOfProductsDTO?){
        if((productPage == null || productPage.products.isEmpty()) && currentPage == 0){
            _isFounded.postValue(false)
        }
        isLastPage = productPage?.isLastPage ?: true
        currentPage++
        if(productPage != null && productPage.products.isNotEmpty()){
            _products.value?.addAll(productPage.products)
            _isFounded.postValue(true)
            _products.postValue(_products.value)
        }
    }

    private suspend fun setNewPage(productPage: LentaPageOfProductsDTO?){
        if((productPage == null || productPage.products.isEmpty()) && currentPage == 0){
            _isFounded.postValue(false)
        }
        isLastPage = productPage?.isLastPage ?: true
        if(productPage != null && productPage.products.isNotEmpty()){
            _isFounded.postValue(true)
            _products.postValue(productPage.products.toMutableList())
        }
    }

    fun download(searchKey: String){
        Log.d("download", searchKey)
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            val pageOfProducts = downloadNewPage(
                searchKey = searchKey,
                pageSelected = currentPage
            )
            addNewPage(pageOfProducts)
            isLoading = false
        }
    }

}