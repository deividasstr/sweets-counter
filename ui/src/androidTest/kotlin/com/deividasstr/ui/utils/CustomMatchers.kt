package com.deividasstr.ui.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.get
import androidx.test.espresso.matcher.BoundedMatcher
import com.robinhood.ticker.TickerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class CustomMatchers {

    companion object {
        fun hasColor(colorResId: Int): Matcher<View> {
            return object : BoundedMatcher<View, TextView>(TextView::class.java) {
                private var context: Context? = null

                override fun matchesSafely(view: TextView): Boolean {
                    context = view.context

                    val textViewColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        (view.compoundDrawables[2] as GradientDrawable).color?.defaultColor
                    } else {
                        (view.compoundDrawables[2] as Drawable).current.toBitmap(1, 1)[0, 0]
                    }

                    val expectedColor = ContextCompat.getColor(context!!, colorResId)

                    return textViewColor == expectedColor
                }

                override fun describeTo(description: Description) {
                    var colorId = colorResId.toString()
                    if (context != null) {
                        colorId = context!!.resources.getResourceName(colorResId)
                    }
                    description.appendText("has color with ID $colorId")
                }
            }
        }

        fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description) {
                    description.appendText("position $childPosition of parent ")
                    parentMatcher.describeTo(description)
                }

                public override fun matchesSafely(view: View): Boolean {
                    if (view.parent !is ViewGroup) return false
                    val parent = view.parent as ViewGroup

                    return (parentMatcher.matches(parent) &&
                        parent.childCount > childPosition &&
                        parent.getChildAt(childPosition) == view)
                }
            }
        }

        fun tickerWithText(matcher: Matcher<String>): BoundedMatcher<View, TickerView> {
            return object : BoundedMatcher<View, TickerView>(TickerView::class.java) {

                override fun matchesSafely(view: TickerView): Boolean {
                    val text = view.text.toString()
                    if (matcher.matches(text)) {
                        return true
                    }
                    return false
                }

                override fun describeTo(description: Description) {
                    description.appendText("with text: ")
                    matcher.describeTo(description)
                }
            }
        }
    }
}
