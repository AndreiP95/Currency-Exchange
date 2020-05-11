package com.example.currencyexchange.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchange.R
import com.example.currencyexchange.network.fxdata.model.FxData
import kotlinx.android.synthetic.main.item_curency_value.view.*

class CurrenciesAdapter :
    RecyclerView.Adapter<CurrenciesAdapter.CurrencyViewHolder>(
    ) {

    private lateinit var baseCurrency: String
    private lateinit var keys: MutableList<String>
    private lateinit var values: MutableList<Double>

    fun updateRatesList(updatedFxData: FxData) {
        keys = updatedFxData.rates.keys.toMutableList()
        values = updatedFxData.rates.values.toMutableList()

        keys.indexOf(baseCurrency).takeUnless { it == -1 }?.let {
            keys.removeAt(it)
            values.removeAt(it)
        }
    }

    fun updateBaseCurrency(baseCurrency: String) {
        this.baseCurrency = baseCurrency
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val currencyView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_curency_value, parent, false)

        return CurrencyViewHolder(currencyView)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val fxDataText = "$baseCurrency = ${String.format(
            "%.4f",
            values.get(position)
        )} ${keys.get(position)}"

        holder.currencyLayout.textview.text = fxDataText
    }

    override fun getItemCount(): Int {
        if (::keys.isInitialized)
            return keys.size
        return 0
    }

    class CurrencyViewHolder(val currencyLayout: View) : RecyclerView.ViewHolder(currencyLayout)

}