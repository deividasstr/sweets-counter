package com.deividasstr.ui.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class CustomMachers {

    companion object {
        fun hasColor(colorResId: Int): Matcher<View> {
            return object : BoundedMatcher<View, AppCompatImageView>(AppCompatImageView::class.java) {
                private var context: Context? = null

                override fun matchesSafely(imageView: AppCompatImageView): Boolean {
                    context = imageView.context

                    val textViewColor = (imageView.background as ColorDrawable).color
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