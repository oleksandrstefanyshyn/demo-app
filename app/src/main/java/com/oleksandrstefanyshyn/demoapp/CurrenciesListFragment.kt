package com.oleksandrstefanyshyn.demoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_currencies_list.*

class CurrenciesListFragment : Fragment() {
    private val currenciesApi: CurrenciesApi by lazy {
        injector.currenciesApi
    }
    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_currencies_list, container, false)

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
        }.also {
            disposable.add(it)
        }

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
            ).also {
                disposable.add(it)
            }
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}
