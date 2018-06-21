package com.deividasstr.ui.base.framework

import android.databinding.BindingAdapter
import android.support.v7.widget.SearchView

/*
@BindingAdapter("onSearch")
fun onSearch(view: SearchView, block: (CharSequence)) {
    view.setOnQueryTextListener(object SearchView.OnQueryTextListener() {

    })
}*/

/*@BindingAdapter("android:onSearch")
fun setListener(view: SearchView, listener: OnSearchQueryChange) {
    view.setOnQueryTextListener(listener)
}*/

interface OnSearchQueryChange {
    fun onSearchQueryChange(newText: String)
}

@BindingAdapter("android:onSearch")
fun setSearchQueryListener(
    view: SearchView,
    change: OnSearchQueryChange?
) {
    if (change == null) {
        view.setOnQueryTextListener(null)
    } else {
        view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                change.onSearchQueryChange(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                change.onSearchQueryChange(newText)
                return true
            }
        })
    }
}


