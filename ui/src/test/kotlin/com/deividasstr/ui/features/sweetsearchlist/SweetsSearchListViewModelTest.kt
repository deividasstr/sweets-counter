package com.deividasstr.ui.features.sweetsearchlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.deividasstr.data.DbSweet
import com.deividasstr.data.store.datasource.SweetSearchDataSource
import com.deividasstr.data.store.dbs.DbSweet
import com.deividasstr.data.store.dbs.SweetsDb
import com.deividasstr.testutils.TestData
import com.deividasstr.testutils.UnitTest
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.utils.UiTestData
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.willReturn
import org.amshove.kluent.`should be`
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock

class SweetsSearchListViewModelTest : UnitTest() {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SweetsSearchListViewModel

    @Mock
    private lateinit var db: SweetsDb

    @Before
    fun before() {
        val dataSource1: DataSource<Int, DbSweet> = FakeDataSource(
            listOf(
                DbSweet(TestData.TEST_SWEET),
                DbSweet(TestData.TEST_SWEET2)
            )
        )

        val dataSource2: DataSource<Int, DbSweet> = FakeDataSource(
            listOf(
                DbSweet(TestData.TEST_SWEET2)
            )
        )

        given { db.query("") } willReturn { dataSource1 }
        given { db.query(UiTestData.UI_SWEET2.name) } willReturn { dataSource2 }

        val factory = SweetSearchDataSourceFactory(SweetSearchDataSource(db))
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

class FakeDataSource<T>(private val itemList: List<T>) : PositionalDataSource<T>() {
    override fun isInvalid(): Boolean = false

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        val items = itemList.subList(params.startPosition, params.startPosition + params.loadSize).toMutableList()
        callback.onResult(items)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        callback.onResult(itemList, 0, itemList.size)
    }
}
