package com.deividasstr.ui.utils

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class CustomMachers {

    companion object {
        fun hasColor(colorResId: Int): Matcher<View> {
            return object : BoundedMatcher<View, TextView>(TextView::class.java) {
                private var context: Context? = null

                override fun matchesSafely(view: TextView): Boolean {
                    context = view.context

                    val textViewColor = (view.compoundDrawables[2] as GradientDrawable).color?.defaultColor
                    val expectedColor = if (Build.VERSION.SDK_INT <= 22) {
                        context!!.resources.getColor(colorResId, null)
                    } else {
                        context!!.getColor(colorResId)
                    }

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
    }
}