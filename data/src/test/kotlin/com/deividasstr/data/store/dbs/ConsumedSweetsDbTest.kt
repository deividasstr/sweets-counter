package com.deividasstr.data.store.dbs

import com.deividasstr.data.DataTestData
import com.deividasstr.data.store.AbstractObjectBoxTest
import com.deividasstr.data.store.models.ConsumedSweetDb
import com.deividasstr.domain.common.assertResultValue
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class ConsumedSweetsDbTest : AbstractObjectBoxTest() {

    private lateinit var db: ConsumedSweetsDb
    private lateinit var testSubscriber: TestObserver<Any>

    @Before
    fun setup() {
        db = ConsumedSweetsDb(store.boxFor(ConsumedSweetDb::class.java))
        testSubscriber = TestObserver()
    }

    @Test
    fun shouldAddSweet() {
        db.addSweet(DataTestData.TEST_CONSUMED_SWEETMODEL).subscribe(testSubscriber)

        testSubscriber.await()
        testSubscriber.assertComplete()
    }

    @Test
    fun shouldGetAllConsumedSweets() {
        db.addSweet(DataTestData.TEST_CONSUMED_SWEETMODEL).blockingAwait()
        db.addSweet(DataTestData.TEST_CONSUMED_SWEETMODEL2).blockingAwait()
        db.addSweet(DataTestData.TEST_CONSUMED_SWEETMODEL3_DAY_AFTER_TOMORROW).blockingAwait()

        db.getAllConsumedSweets().subscribe(testSubscriber)

        testSubscriber.assertResultValue(listOf(
            DataTestData.TEST_CONSUMED_SWEETMODEL,
            DataTestData.TEST_CONSUMED_SWEETMODEL2,
            DataTestData.TEST_CONSUMED_SWEETMODEL3_DAY_AFTER_TOMORROW))
    }

    @Test
    fun shouldGetAllConsumedSweets_returnsEmptyList() {
        db.getAllConsumedSweets().subscribe(testSubscriber)

        testSubscriber.assertResultValue(emptyList<ConsumedSweetDb>())
    }
}