package com.deividasstr.ui.features.consumedsweethistory

import android.content.Context
import com.deividasstr.domain.enums.MeasurementUnit
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.R
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.jaychang.srv.kae.SimpleCell
import com.jaychang.srv.kae.SimpleViewHolder
import kotlinx.android.synthetic.main.item_consumed_sweet.view.*

class ConsumedSweetCell(
    val item: ConsumedSweetUi,
    private val dateTimeHandler: DateTimeHandler,
    private val measurementUnit: MeasurementUnit
) :
    SimpleCell<ConsumedSweetUi>(item) {

    override fun getLayoutRes(): Int {
        return R.layout.item_consumed_sweet
    }

    override fun onBindViewHolder(
        holder: SimpleViewHolder,
        position: Int,
        context: Context,
        payload: Any?
    ) {
        with(holder.itemView) {
            val sweet = item.sweet
            val consumedSweet = item
            val data: String = formatDataText(context, consumedSweet)
            name_consumed_sweet.text = sweet.name
            data_consumed_sweet.text = data
        }
    }

    private fun formatDataText(
        context: Context,
        consumedSweet: ConsumedSweetUi
    ): String {
        val date = context.getString(
            R.string.at_time_formatted,
            dateTimeHandler.formattedTime(consumedSweet.date))

        val cals = (consumedSweet.g * consumedSweet.sweet.calsPer100 / 100).toInt()
        val calsString = context.getString(R.string.cals_formatted, cals)

        val consumedAmount = when (measurementUnit) {
            MeasurementUnit.GRAM -> context.getString(
                R.string.unit_grams_short_formatted,
                consumedSweet.g)
            MeasurementUnit.OUNCE -> context.getString(
                R.string.unit_grams_short_formatted,
                consumedSweet.g / MeasurementUnit.OUNCE.ratioWithGrams)
        }

        return date.plus(", ").plus(calsString).plus(", ").plus(consumedAmount)
    }
}