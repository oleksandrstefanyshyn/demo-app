package com.oleksandrstefanyshyn.demoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_currency.view.*

class CurrenciesAdapter : RecyclerView.Adapter<CurrenciesViewHolder>() {
    private val clickSubject = PublishSubject.create<String>()
    private var currencies: List<String> = emptyList()
    val clickEvent: Observable<String> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency, parent, false)
            .let(::CurrenciesViewHolder)

    override fun getItemCount(): Int = currencies.size

    override fun onBindViewHolder(holder: CurrenciesViewHolder, position: Int) {
        holder.containerView.apply {
            currency_name.text = currencies[position]
            setOnClickListener {
                clickSubject.onNext(currencies[holder.adapterPosition])
            }
        }
    }

    fun update(list: List<String>) {
        currencies = list
        notifyDataSetChanged()
    }

}

class CurrenciesViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer
