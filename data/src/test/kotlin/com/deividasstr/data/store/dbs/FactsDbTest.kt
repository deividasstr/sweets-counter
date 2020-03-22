package com.deividasstr.data.store.dbs

import com.deividasstr.data.DataTestData
import com.deividasstr.data.R
import com.deividasstr.data.store.AbstractObjectBoxTest
import com.deividasstr.data.store.models.FactDb
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either
import com.deividasstr.testutils.runBlock
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class FactsDbTest : AbstractObjectBoxTest() {

    private lateinit var db: FactsDb

    @Before
    fun setup() {
        db = FactsDb(store.boxFor(FactDb::class.java))
    }

    @Test
    fun shouldAddFacts() = runBlock {
        val result = DataTestData.TEST_FACTMODEL_1
        db.addFacts(DataTestData.TEST_FACT_LIST)
        db.getRandomFact(DataTestData.TEST_FACTMODEL_2.id) shouldEqual Either.Right(result)
    }

    @Test
    fun shouldGetRandomFact_whenEmptyDb_thenReturnsNPE() =
        runBlock {
            db.addFacts(emptyList())

            db.getRandomFact(-1) shouldEqual Either.Left(Error(R.string.error_no_facts_available))
        }

    @Test
    fun shouldGetRandomFact() = runBlock {
        val result = DataTestData.TEST_FACTMODEL_2
        db.addFacts(DataTestData.TEST_FACT_LIST)
        db.getRandomFact(DataTestData.TEST_FACTMODEL_1.id) shouldEqual Either.Right(result)
    }
}
