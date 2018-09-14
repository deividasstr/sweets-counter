package com.deividasstr.ui.features.consumedsweetdata

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.deividasstr.ui.features.consumedsweetdata.utils.Consts.INFINITY_IMITATION

class PeriodPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    private var currentPosition = -1

    override fun getItem(position: Int): Fragment {
        return ConsumedPeriodFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return INFINITY_IMITATION
    }

    // Refreshing the viewpager size
    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        if (position != currentPosition) {
            val fragment = `object` as Fragment
            val pager = container as ResizableViewPager
            if (fragment.view != null) {
                currentPosition = position
                pager.measureCurrentView(fragment.view!!)
            }
        }
    }
}
