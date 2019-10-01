package com.oleksandrstefanyshyn.demoapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_currencies_list.*

class CurrenciesListFragment : BaseFragment() {
    private val currenciesApi: CurrenciesApi by lazy {
        injector.currenciesApi
    }
    override val layoutId: Int = R.layout.fragment_currencies_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currenciesAdapter = CurrenciesAdapter()

        currencies_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = currenciesAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        currenciesAdapter.clickEvent.subscribe {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.toDestroy()

        currenciesApi.currenciesList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { currencies -> currenciesAdapter.update(currencies.rates.keys.toList()) },
                {
                    Toast.makeText(
                        requireContext(),
                        "Cannot get currencies!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ).toDestroy()
    }
}
