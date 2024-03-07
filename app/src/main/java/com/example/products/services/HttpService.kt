package com.example.products.services

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.RuntimeException
import java.net.URL

object HttpService {

    fun sendGetRequest(requestUrl: String): String{
        Log.i("sendGetRequest", "requestUrl: $requestUrl")
        return call(
            Request.Builder()
                .get()
                .url(URL(requestUrl))
                .build()
        )
    }

    private fun call(request: Request): String {
        val response = OkHttpClient().newCall(request).execute()
        if (response.code != 200){
            Log.e("Error HttpService.call", "http code: ${response.code}")
            Log.e("Error HttpService.call", "http message: ${response.message}")
            throw RuntimeException(response.message)
        }
        return response.body!!.string()
    }

}