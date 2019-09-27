package com.oleksandrstefanyshyn.demoapp

import retrofit2.Call
import retrofit2.http.GET
import java.math.BigDecimal

interface CurrenciesApi {

    @GET("latest")
    fun currenciesList() : Call<Currencies>
}

data class Currencies(val rates: Map<String, BigDecimal>)
