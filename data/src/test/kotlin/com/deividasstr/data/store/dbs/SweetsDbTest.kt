package com.deividasstr.data.store.dbs

import com.deividasstr.data.DataTestData
import com.deividasstr.data.R
import com.deividasstr.data.store.AbstractObjectBoxTest
import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either
import com.deividasstr.testutils.runBlock
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class SweetsDbTest : AbstractObjectBoxTest() {

    private lateinit var db: SweetsDb

    @Before
    fun setup() {
        db = SweetsDb(store.boxFor(SweetDb::class.java))
    }

    @Test
    fun shouldAddSweets() = runBlock {
        val result = DataTestData.TEST_LIST_SWEETMODELS
        db.addSweets(result)
        db.getAllSweets() shouldEqual Either.Right(result)
    }

    @Test
    fun shouldAddSweet() = runBlock {
        val result = DataTestData.TEST_SWEETMODEL
        db.addSweet(result)
        db.getAllSweets() shouldEqual Either.Right(listOf(result))
    }

    @Test
    fun shouldGetSweetById() = runBlock {
        val result = DataTestData.TEST_SWEETMODEL
        db.addSweets(DataTestData.TEST_LIST_SWEETMODELS)
        db.getSweetById(result.id) shouldEqual Either.Right(result)
    }

    @Test
    fun shouldGetSweetById_noItemFound() = runBlock {
        db.getSweetById(DataTestData.TEST_SWEETMODEL.id) shouldEqual Either.Left(Error(R.string.error_item_not_found))
    }

    @Test
    fun queryEmpty_returnsAll() = runBlock {
        db.addSweets(DataTestData.TEST_LIST_SWEETMODELS)

        val sweets = db.query("").find()

        assert(DataTestData.TEST_LIST_SWEETMODELS[0] == sweets[0])
        assert(DataTestData.TEST_LIST_SWEETMODELS[1] == sweets[1])
        assert(DataTestData.TEST_LIST_SWEETMODELS.size == sweets.size)
    }

    @Test
    fun queryCorrectName_returnsOne() = runBlock {
        db.addSweets(DataTestData.TEST_LIST_SWEETMODELS)

        val sweets = db.query(DataTestData.TEST_LIST_SWEETMODELS[1].name).find()

        assert(DataTestData.TEST_LIST_SWEETMODELS[1] == sweets[0])
        assert(sweets.size == 1)
    }
}
