package com.deividasstr.data.store.dbs

import androidx.paging.PagedList
import com.deividasstr.data.DataTestData
import com.deividasstr.data.R
import com.deividasstr.data.store.AbstractDbTest
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either
import com.deividasstr.testutils.runBlock
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Executors

class SweetsDbTest : AbstractDbTest() {

    private lateinit var database: SweetsDb

    @Before
    fun setup() {
        database = SweetsDb(baseDb.sweetsDbQueries)
    }

    @Test
    fun shouldGetSweetById() = runBlock {
        val result = DataTestData.TEST_SWEETMODEL
        database.addSweets(DataTestData.TEST_LIST_SWEETMODELS)
        database.getSweetById(result.id) shouldEqual Either.Right(result)
    }

    @Test
    fun shouldGetSweetById_noItemFound() = runBlock {
        database.getSweetById(DataTestData.TEST_SWEETMODEL.id) shouldEqual Either.Left(Error(R.string.error_item_not_found))
    }

    @Test
    fun queryEmpty_returnsAll() = runBlock {
        database.addSweets(DataTestData.TEST_LIST_SWEETMODELS)

        val source = database.query("")
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setPrefetchDistance(20)
            .build()

        val sweets = PagedList(source, config, Executors.newSingleThreadExecutor(), Executors.newSingleThreadExecutor())

        assert(DataTestData.TEST_LIST_SWEETMODELS[0] == sweets[0])
        assert(DataTestData.TEST_LIST_SWEETMODELS[1] == sweets[1])
        assert(DataTestData.TEST_LIST_SWEETMODELS.size == sweets.size)
    }

    @Test
    fun queryCorrectName_returnsOne() = runBlock {
        database.addSweets(DataTestData.TEST_LIST_SWEETMODELS)

        val source = database.query(DataTestData.TEST_LIST_SWEETMODELS[1].name)
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setPrefetchDistance(20)
            .setInitialLoadSizeHint(20)
            .build()

        val sweets = PagedList(
            source,
            config,
            Executors.newSingleThreadExecutor(),
            Executors.newSingleThreadExecutor()
        )

        assert(DataTestData.TEST_LIST_SWEETMODELS[1] == sweets[0])
        assert(sweets.size == 1)
    }
}
