package com.deividasstr.ui.features.consumedsweethistory

import android.view.LayoutInflater
import android.view.ViewGroup
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.R
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.base.models.SweetUi
import kotlinx.android.synthetic.main.header_consumed_sweet.view.*
import kotlinx.android.synthetic.main.item_consumed_sweet.view.*
import org.zakariya.stickyheaders.SectioningAdapter

class ConsumedSweetsHistoryAdapter(private val dateTimeHandler: DateTimeHandler) :
    SectioningAdapter() {

    private var consumedSweetsByDate: List<Pair<String, List<ConsumedSweetUi>>> = emptyList()
    private lateinit var sweets: List<SweetUi>

    fun setSweets(consumedSweets: List<ConsumedSweetUi>, sweets: List<SweetUi>) {
        this.sweets = sweets
        consumedSweetsByDate =
            consumedSweets.groupBy { dateTimeHandler.formattedDate(it.date) }.toList()
        notifyAllSectionsDataSetChanged()
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup, headerType: Int): HeaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.header_consumed_sweet, parent, false)
        return HeaderViewHolder(v)
    }

    override fun onBindHeaderViewHolder(viewHolder: SectioningAdapter.HeaderViewHolder,
        index: Int,
        headerType: Int) {
        viewHolder.itemView.date_consumed_sweet.text = consumedSweetsByDate[index].first
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, itemType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_consumed_sweet, parent, false)
        return ItemViewHolder(v)
    }

    override fun onBindItemViewHolder(viewHolder: SectioningAdapter.ItemViewHolder,
        sectionIndex: Int,
        itemIndex: Int,
        itemType: Int) {

        val consumedSweet = consumedSweetsByDate[sectionIndex].second[itemIndex]
        val sweet = sweets.find { it.id == consumedSweet.sweetId.toLong() }!!
        val view = viewHolder.itemView

        view.name_consumed_sweet.text = sweet.name
        view.time_consumed_sweet.text = dateTimeHandler.formattedTime(consumedSweet.date)
        view.cals_consumed_sweet.text =
            (consumedSweet.g * sweet.calsPer100 / 100).toInt().toString()
        view.units_consumed_sweet.text = consumedSweet.g.toString()
    }

    override fun getNumberOfSections(): Int {
        return consumedSweetsByDate.size
    }

    override fun getNumberOfItemsInSection(sectionIndex: Int): Int {
        return consumedSweetsByDate[sectionIndex].second.size
    }

    override fun doesSectionHaveHeader(sectionIndex: Int): Boolean {
        return true
    }
}