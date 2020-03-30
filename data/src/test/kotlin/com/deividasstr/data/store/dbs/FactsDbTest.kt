package com.deividasstr.data.store.dbs

import com.deividasstr.data.DataTestData
import com.deividasstr.data.R
import com.deividasstr.data.store.AbstractDbTest
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either
import com.deividasstr.testutils.runBlock
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class FactsDbTest : AbstractDbTest() {

    private lateinit var database: FactsDb

    @Before
    fun setup() {
        database = FactsDb(baseDb.factsDbQueries)
    }

    @Test
    fun shouldAddFacts() = runBlock {
        val result = DataTestData.TEST_FACTMODEL_1
        database.addFacts(DataTestData.TEST_FACT_LIST)
        database.getFact(DataTestData.TEST_FACTMODEL_2.id) shouldEqual Either.Right(result)
    }

    @Test
    fun shouldGetRandomFact_whenEmptyDb_thenReturnsError() =
        runBlock {
            database.addFacts(emptyList())
            database.getFact(-1) shouldEqual Either.Left(Error(R.string.error_no_facts_available))
        }

    @Test
    fun shouldGetRandomFact() = runBlock {
        val result = DataTestData.TEST_FACTMODEL_2
        database.addFacts(DataTestData.TEST_FACT_LIST)
        database.getFact(DataTestData.TEST_FACTMODEL_1.id) shouldEqual Either.Right(result)
    }
}
