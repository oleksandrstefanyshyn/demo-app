package com.oleksandrstefanyshyn.demoapp

class Exchager {
    fun exchange(amound: Double, rate: Double): Double {
        if (amound < 0 || rate <= 0) {
            throw IllegalArgumentException()
        }
        return amound * rate
    }
}
