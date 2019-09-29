package com.oleksandrstefanyshyn.demoapp

import io.reactivex.Single
import retrofit2.http.GET
import java.math.BigDecimal

interface CurrenciesApi {

    @GET("latest")
    fun currenciesList(): Single<Currencies>
}

data class Currencies(val rates: Map<String, BigDecimal>)
