package com.deividasstr.ui.features.sweetsearchlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagedList
import com.deividasstr.data.store.dbs.SweetsDb
import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.utils.AbstractObjectBoxTest
import com.deividasstr.utils.UiTestData
import org.amshove.kluent.`should be`
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SweetsSearchListViewModelTest : AbstractObjectBoxTest() {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SweetsSearchListViewModel
    private lateinit var db: SweetsDb

    @Before
    fun before() {
        db = SweetsDb(store.boxFor(SweetDb::class.java))
        db.addSweets(UiTestData.TEST_LIST_SWEETMODELS).blockingAwait()
        val factory = SweetSearchDataSourceFactory(db)
        viewModel = SweetsSearchListViewModel(factory)
    }

    @Test
    fun shouldSetQuery() {
        val query = "query"
        viewModel.query `should be` ("")
        viewModel.searchSweets(query)
        viewModel.query `should be` (query)
    }

    @Test
    fun shouldObserveUiSweets_emptyQuery_returnAll() {
        viewModel.searchSweets("")

        viewModel.sweets.observeForever { list: PagedList<SweetUi>? ->
            assertEquals(UiTestData.UI_SWEET1, list!![0])
            assertEquals(UiTestData.UI_SWEET2, list[1])
        }
    }

    @Test
    fun shouldObserveUiSweets_queryName_return1() {
        viewModel.searchSweets(UiTestData.UI_SWEET2.name)
        viewModel.sweets.observeForever { list: PagedList<SweetUi>? ->
            assertEquals(UiTestData.UI_SWEET2, list!![0])
            assertEquals(1, list.size)
        }
    }
}