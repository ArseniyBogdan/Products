package com.example.products.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.products.R
import com.example.products.usecases.GetPageOfProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val getPageOfProductsUseCase = GetPageOfProductsUseCase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch(Dispatchers.IO) {
            val response = getPageOfProductsUseCase.execute(0, 20)
            Log.d("Response:", response.toString())
        }
    }
}