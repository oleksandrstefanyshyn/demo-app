package com.oleksandrstefanyshyn.demoapp

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_exchange.*

class ExchangeFragment : BaseFragment() {
    override val layoutId: Int = R.layout.fragment_exchange
    val args: ExchangeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currency_name.text = args.currencyName
    }
}
