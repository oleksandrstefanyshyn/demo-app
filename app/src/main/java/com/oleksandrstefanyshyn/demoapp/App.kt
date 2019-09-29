package com.oleksandrstefanyshyn.demoapp

import android.app.Application

class App : Application(), DaggerComponentProvider {
    override val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .build()
    }
}
