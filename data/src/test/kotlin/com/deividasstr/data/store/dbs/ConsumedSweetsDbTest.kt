package com.deividasstr.data.store.dbs

import com.deividasstr.data.DataTestData
import com.deividasstr.data.store.AbstractObjectBoxTest
import com.deividasstr.data.store.models.ConsumedSweetDb
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.utils.runBlock
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class ConsumedSweetsDbTest : AbstractObjectBoxTest() {

    private lateinit var db: ConsumedSweetsDb

    @Before
    fun setup() {
        db = ConsumedSweetsDb(store.boxFor(ConsumedSweetDb::class.java))
    }

    @Test
    fun shouldAddSweet() = runBlock {
        db.addSweet(DataTestData.TEST_CONSUMED_SWEETMODEL2) shouldEqual Either.Right(Either.None())
    }

    @Test
    fun shouldGetAllConsumedSweets() = runBlock {
        db.addSweet(DataTestData.TEST_CONSUMED_SWEETMODEL)
        db.addSweet(DataTestData.TEST_CONSUMED_SWEETMODEL3_DAY_AFTER_TOMORROW)

        db.getAllConsumedSweets().getValue() shouldEqual listOf(
            DataTestData.TEST_CONSUMED_SWEETMODEL3_DAY_AFTER_TOMORROW,
            DataTestData.TEST_CONSUMED_SWEETMODEL)
    }

    @Test
    fun shouldGetAllConsumedSweets_returnsEmptyList() = runBlock {
        db.getAllConsumedSweets().getValue() shouldEqual emptyList<ConsumedSweetDb>()
    }
}

