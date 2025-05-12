package com.example.shopapp.infrastracture.datasources.remote


import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.100:5000/api/"

    private fun getRetrofit(context: Context): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun <T> create(service: Class<T>, context: Context): T {
        return getRetrofit(context).create(service)
    }
}