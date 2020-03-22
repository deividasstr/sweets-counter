package com.deividasstr.ui.features.consumedsweetdata

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

class ResizableViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private var currentView: View? = null

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (currentView == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        } else {
            var height = 0
            currentView!!.measure(
                widthMeasureSpec,
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            val h = currentView!!.measuredHeight
            if (h > height) height = h
            val newHeightMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)

            super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
        }
    }

    fun measureCurrentView(currentView: View) {
        this.currentView = currentView
        requestLayout()
    }
}
