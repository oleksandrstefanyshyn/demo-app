package com.oleksandrstefanyshyn.demoapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_exchange.*
import java.math.BigDecimal

class ExchangeFragment : BaseFragment() {
    private val currenciesApi: CurrenciesApi by lazy {
        injector.currenciesApi
    }
    val args: ExchangeFragmentArgs by navArgs()
    override val layoutId: Int = R.layout.fragment_exchange

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currency_name.text = args.currencyName

        currenciesApi.currenciesList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { currencies ->
                    initSpinner(currencies.rates)
                },
                {
                    Toast.makeText(
                        requireContext(),
                        "Cannot get currencies!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ).toDestroy()
    }

    private fun initSpinner(rates: Map<String, BigDecimal>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            rates.keys.toList()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        exchange_currency_spinner.adapter = adapter

        exchange_currency_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val currency = parent.getItemAtPosition(position)
                    exchange_currency_value.text = rates[currency].toString()
                }
            }
    }
}
