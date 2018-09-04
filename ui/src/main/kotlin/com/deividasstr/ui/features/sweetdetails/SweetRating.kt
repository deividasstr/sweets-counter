package com.deividasstr.ui.features.sweetdetails

import com.deividasstr.ui.R

enum class SweetRating {
    BAD {
        override fun nameStringRes(): Int = R.string.sweet_rating_bad
    },
    AVERAGE {
        override fun nameStringRes(): Int = R.string.sweet_rating_average
    },
    GOOD {
        override fun nameStringRes(): Int = R.string.sweet_rating_good
    };

    abstract fun nameStringRes(): Int
}