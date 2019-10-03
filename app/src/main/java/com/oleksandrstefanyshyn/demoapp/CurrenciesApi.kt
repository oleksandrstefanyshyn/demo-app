package com.oleksandrstefanyshyn.demoapp

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesApi {

    @GET("latest")
    fun currenciesList(): Single<Currencies>

    @GET("latest")
    fun exangeRates(@Query("base") base: String): Single<Currencies>
}

data class Currencies(val rates: Map<String, Double>)
