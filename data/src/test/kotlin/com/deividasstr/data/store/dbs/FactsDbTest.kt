package com.deividasstr.data.store.dbs

import com.deividasstr.data.DataTestData
import com.deividasstr.data.store.AbstractObjectBoxTest
import com.deividasstr.data.store.models.FactModel
import com.deividasstr.domain.common.assertResultValue
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class FactsDbTest : AbstractObjectBoxTest() {

    private lateinit var db: FactsDb
    private lateinit var testSubscriber: TestObserver<Any>

    @Before
    fun setup() {
        db = FactsDb(store.boxFor(FactModel::class.java))
        testSubscriber = TestObserver()
    }

    @Test
    fun shouldAddFacts() {
        db.addFacts(DataTestData.TEST_FACT_LIST).blockingAwait()

        db.getRandomFact(DataTestData.TEST_FACTMODEL_2.id).subscribe(testSubscriber)

        testSubscriber.assertResultValue(DataTestData.TEST_FACTMODEL_1)
    }

    @Test
    fun shouldGetRandomFact_whenEmptyDb_thenReturnsNPE() {
        db.addFacts(emptyList()).blockingAwait()

        db.getRandomFact(-1).subscribe(testSubscriber)

        testSubscriber.assertNotComplete()
        testSubscriber.assertError(NullPointerException::class.java)
    }

    @Test
    fun shouldGetRandomFact() {
        db.addFacts(DataTestData.TEST_FACT_LIST).blockingAwait()

        db.getRandomFact(DataTestData.TEST_FACTMODEL_2.id).subscribe(testSubscriber)

        testSubscriber.assertResultValue(DataTestData.TEST_FACTMODEL_1)
    }

    @Test
    fun shouldGetLastUpdateTimeStamp() {
        db.addFacts(DataTestData.TEST_FACT_LIST).blockingAwait()

        db.getLastUpdateTimeStamp().subscribe(testSubscriber)

        testSubscriber.assertResultValue(DataTestData.TEST_FACTMODEL_2.addedTimestamp)
    }
}