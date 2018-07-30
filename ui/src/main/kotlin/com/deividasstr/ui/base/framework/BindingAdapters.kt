package com.deividasstr.ui.base.framework

import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter

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

@BindingAdapter("android:setQuery")
fun setQuery(view: SearchView, query: String) {
    view.setQuery(query, false)
}


