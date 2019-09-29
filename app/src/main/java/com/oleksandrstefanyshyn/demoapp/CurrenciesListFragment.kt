package com.oleksandrstefanyshyn.demoapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

private const val TAG = "CurrenciesListFragment"

class CurrenciesListFragment : Fragment() {
    private val currenciesApi: CurrenciesApi by lazy {
        injector.currenciesApi
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_currencies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val disposable = currenciesApi.currenciesList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { currencies -> Log.d(TAG, "Currencies: $currencies") },
                {
                    Toast.makeText(
                        requireContext(),
                        "Cannot get currencies!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
    }
}
