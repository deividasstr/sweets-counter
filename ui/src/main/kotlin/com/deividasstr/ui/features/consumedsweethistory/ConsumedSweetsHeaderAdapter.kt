package com.deividasstr.ui.features.consumedsweethistory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.R
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.jaychang.srv.decoration.SimpleSectionHeaderProvider
import kotlinx.android.synthetic.main.header_consumed_sweet.view.*

class ConsumedSweetsHeaderAdapter(
    private val dateTimeHandler: DateTimeHandler,
    private val inflater: LayoutInflater
) : SimpleSectionHeaderProvider<ConsumedSweetUi>() {
    override fun isSameSection(
        item: ConsumedSweetUi,
        nextItem: ConsumedSweetUi
    ): Boolean {
        return dateTimeHandler.areDatesSameDay(item.date, nextItem.date)
    }

    @SuppressLint("InflateParams") // Lib does not pass parent view
    override fun getSectionHeaderView(item: ConsumedSweetUi, position: Int): View {
        val view = inflater
            .inflate(R.layout.header_consumed_sweet, null, false)
        view.date_consumed_sweet.text =
            dateTimeHandler.formattedDateFull(item.date)
        return view
    }

    override fun getSectionHeaderMarginTop(item: ConsumedSweetUi, position: Int): Int {
        return 0
    }

    override fun isSticky(): Boolean {
        return true
    }
}