package com.oleksandrstefanyshyn.demoapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.exchangeratesapi.io/"

object RetrofitModule {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val currenciesApi = retrofit.create(CurrenciesApi::class.java)

}