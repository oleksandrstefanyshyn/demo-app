package com.oleksandrstefanyshyn.demoapp

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface ApplicationComponent {
    val currenciesApi: CurrenciesApi
}
