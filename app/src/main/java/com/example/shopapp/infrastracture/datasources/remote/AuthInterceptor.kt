package com.example.shopapp.infrastracture.datasources.remote


import android.content.Context
import android.util.Log
import com.example.shopapp.infrastracture.datasources.local.SettingsDataStore
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.Interceptor
import okhttp3.Response
import kotlinx.coroutines.runBlocking

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            SettingsDataStore.tokenFlow(context).firstOrNull()
        }
        Log.d("AuthInterceptor", "Token: $token")
        val requestBuilder = chain.request().newBuilder()
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}
