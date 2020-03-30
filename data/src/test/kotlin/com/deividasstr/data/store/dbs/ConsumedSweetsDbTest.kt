package com.deividasstr.data.store.dbs

import com.deividasstr.data.DataTestData
import com.deividasstr.data.store.AbstractDbTest
import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.domain.entities.models.ConsumedSweet
import com.deividasstr.domain.monads.Either
import com.deividasstr.testutils.TestData
import com.deividasstr.testutils.runBlock
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class ConsumedSweetsDbTest : AbstractDbTest() {

    private lateinit var database: ConsumedSweetsDao
    private lateinit var sweetsDb: SweetsDb

    @Before
    fun setup() {
        database = ConsumedSweetsDb(baseDb.consumedSweetsDbQueries)
    }

    @Test
    fun shouldAddSweet() = runBlock {
        database.addSweet(DbConsumedSweet(TestData.TEST_CONSUMED_SWEET2)) shouldEqual Either.Right(
            Either.None())
    }

    @Test
    fun shouldGetAllConsumedSweets() = runBlock {
        val consumedSweet1 = TestData.TEST_CONSUMED_SWEET
        val consumedSweet2 = DataTestData.TEST_CONSUMED_SWEETMODEL3_DAY_AFTER_TOMORROW

        sweetsDb = SweetsDb(baseDb.sweetsDbQueries)
        sweetsDb.addSweet(DbSweet(consumedSweet1.sweet))
        sweetsDb.addSweet(DbSweet(consumedSweet2.sweet))

        database.addSweet(DbConsumedSweet(consumedSweet1))
        database.addSweet(DbConsumedSweet(consumedSweet2))

        database.getAllConsumedSweets().getValue() shouldEqual listOf(
            consumedSweet2,
            consumedSweet1)
    }

    @Test
    fun shouldGetAllConsumedSweets_returnsEmptyList() =
        runBlock {
            database.getAllConsumedSweets().getValue() shouldEqual emptyList<ConsumedSweet>()
        }
}
