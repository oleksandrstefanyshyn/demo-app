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

class ExchangeFragment : BaseFragment() {
    private val currenciesApi: CurrenciesApi by lazy {
        injector.currenciesApi
    }
    private val args: ExchangeFragmentArgs by navArgs()
    private val exchanger = Exchager()
    var currencyValue: Double = 1.0
    override val layoutId: Int = R.layout.fragment_exchange

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rxTextWatcher = RxTextWatcher()
        currency_value.addTextChangedListener(rxTextWatcher)
        rxTextWatcher.textChangeObserver
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                exchange_currency_value.text =
                    formatValue(it.toDouble(), currencyValue)
            }.toDestroy()
        currency_value.setText(currencyValue.toString())
        currency_name.text = args.currencyName

        currenciesApi.exchangeRates(args.currencyName)
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

    private fun initSpinner(rates: Map<String, Double>) {
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
                    val currency = parent.getItemAtPosition(position) as String
                    currencyValue = rates.getValue(currency)
                    exchange_currency_value.text =
                        formatValue(currency_value.text.toString().toDouble(), currencyValue)
                }
            }
    }

    private fun formatValue(amount: Double, rate: Double): String {
        return String.format("%.2f", exchanger.exchange(amount, rate))
    }
}
