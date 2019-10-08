package com.oleksandrstefanyshyn.demoapp

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ExchangerTest {
    lateinit var exchager: Exchager

    @Before
    fun setup() {
        exchager = Exchager()
    }

    @Test
    fun `exchange 100 amount 20 rate returns 2000`() {
        val amount = 100.0
        val rate = 20.0

        val result = exchager.exchange(amount, rate)

        Assert.assertEquals(result, 2000.0, 0.0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `exchange 1000 amount -45 rate throws exceptions`() {
        val amount = 1000.0
        val rate = -45.0

        exchager.exchange(amount, rate)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `exchange -100 amount -80 rate throws exceptions`() {
        val amount = -100.0
        val rate = -80.0

        exchager.exchange(amount, rate)
    }
}
