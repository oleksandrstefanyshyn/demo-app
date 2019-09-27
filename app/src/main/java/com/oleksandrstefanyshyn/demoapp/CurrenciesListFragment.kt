package com.oleksandrstefanyshyn.demoapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "CurrenciesListFragment"

class CurrenciesListFragment : Fragment() {

    private  val currenciesApi = RetrofitModule.currenciesApi

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_currencies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currenciesApi.currenciesList().enqueue(object : Callback<Currencies> {
            override fun onFailure(call: Call<Currencies>, t: Throwable) {
                Log.e(TAG, "onFailure", t)
            }

            override fun onResponse(call: Call<Currencies>, response: Response<Currencies>) {
                Log.d(TAG, "onResponse, ${response.body()}")
            }
        })
    }
}
