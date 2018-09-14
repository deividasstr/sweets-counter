package com.deividasstr.ui.features.sweetsearchlist

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.deividasstr.ui.base.framework.base.BaseViewModel
import com.deividasstr.ui.base.models.SweetUi
import javax.inject.Inject

class SweetsSearchListViewModel
@Inject constructor(private val dataSourceFactory: SweetSearchDataSourceFactory) : BaseViewModel() {

    companion object {
        private const val PAGE_SIZE = 30
        private const val PREFETCH_DISTANCE = 30
    }

    val sweets: LiveData<PagedList<SweetUi>>
    var query: String = ""

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .setEnablePlaceholders(true)
            .build()

        sweets = LivePagedListBuilder(dataSourceFactory, config).build()
    }

    fun searchSweets(text: String) {
        query = text
        dataSourceFactory.search(text)
        sweets.value?.dataSource?.invalidate()
    }
}