package com.deividasstr.ui.features.consumedsweethistory

import android.content.Context
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.R
import com.jaychang.srv.kae.SimpleCell
import com.jaychang.srv.kae.SimpleViewHolder
import kotlinx.android.synthetic.main.item_consumed_sweet.view.*

class ConsumedSweetCell(
    val item: CombinedSweet,
    private val dateTimeHandler: DateTimeHandler
) :
    SimpleCell<CombinedSweet>(item) {

    override fun getLayoutRes(): Int {
        return R.layout.item_consumed_sweet
    }

    override fun onBindViewHolder(
        holder: SimpleViewHolder,
        position: Int,
        context: Context,
        payload: Any?
    ) {

        val view = holder.itemView
        val sweet = item.sweet
        val consumedSweet = item.consumedSweet

        view.name_consumed_sweet.text = sweet.name
        view.time_consumed_sweet.text = dateTimeHandler.formattedTime(consumedSweet.date)
        view.cals_consumed_sweet.text =
            (consumedSweet.g * sweet.calsPer100 / 100).toInt().toString()
        view.units_consumed_sweet.text = consumedSweet.g.toString()
    }
}