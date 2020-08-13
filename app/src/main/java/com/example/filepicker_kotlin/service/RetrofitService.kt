package com.timelysoft.tsjdomcom.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitService {

    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url
            .newBuilder()
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .addHeader("Authorization", "bearer 4Uzo79t7021G712DbNhd0Uv5vUL-olg7K_kHfINhmTh9EW_8A7XfwGsiWa1CU3bdJdFHNFrcZu0QAjFN2Hi-fXpzPF4yvIrqXWYZaeXp0PxZGGjlbZ-4iFi0p518uy7BgUhc_N_2eNsIF1jAqtP3tF9txIclkwNre_NGrl5Ve7YvUvKYXgYY0zRGZTis_1bgXZdEOQPXmOT0OG3i2wJAAGxJTb2dwf2CrpQIxMQZIU5KoA7vnI5eUgPKEZBE4xUNOKOFOKVYcNyFVOkSa1W1NqvrBFwFmrOOHVc_OsXXmcAMaSd7AKSpvfX-ljA8Kj8BjKnqHzrg8bmBz3Ic1g0rPvUFO4XfmuPI_OoH0uQgxTKhOzui5zSyRj1zv56P5ePiICI3nR8altmG_kxTeemeaLXq0HE8rw_qaTzo72f0IbE0R_3Ivkh1ptc961u74p31AtXOP_35c3cC_tRWPn9TQv3pNtHnb99dYNKjfKVmW5moR21dHsLYxj2cB5XBj69WDuKoT7compziA3P2FusLgjfgN2rYnSs53ICNuiuxrEoQUYhhddeXS4DoMEjoOX1-GZQ1nw1dxlr8D3WZym_DCqHX6zSkxbXjYoGuR69Tvrk ")
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val client =
        OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .build()

    private fun retrofit(baseUrl: String = "https://test.tsjdom.com:204/api/"): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    fun apiServiceNew(): ApiService {
        return retrofit().create(ApiService::class.java)
    }

}