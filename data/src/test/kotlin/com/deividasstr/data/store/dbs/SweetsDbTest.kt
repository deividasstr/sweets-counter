package com.deividasstr.data.store.dbs

import com.deividasstr.data.DataTestData
import com.deividasstr.data.store.AbstractObjectBoxTest
import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.common.assertResultValue
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class SweetsDbTest : AbstractObjectBoxTest() {

    private lateinit var db: SweetsDb
    private lateinit var testSubscriber: TestObserver<Any>

    @Before
    fun setup() {
        db = SweetsDb(store.boxFor(SweetDb::class.java))
        testSubscriber = TestObserver()
    }

    @Test
    fun shouldAddSweets() {
        db.addSweets(DataTestData.TEST_LIST_SWEETMODELS).blockingAwait()
        db.getAllSweets().subscribe(testSubscriber)

        testSubscriber.await()

        testSubscriber.assertComplete()
        testSubscriber.assertValue(DataTestData.TEST_LIST_SWEETMODELS)
    }

    @Test
    fun shouldAddSweet() {
        db.addSweet(DataTestData.TEST_SWEETMODEL).blockingAwait()
        db.getAllSweets().subscribe(testSubscriber)

        testSubscriber.await()

        testSubscriber.assertComplete()
        testSubscriber.assertValue(listOf(DataTestData.TEST_SWEETMODEL))
    }

    @Test
    fun shouldGetSweetById() {
        db.addSweets(DataTestData.TEST_LIST_SWEETMODELS).blockingAwait()

        db.getSweetById(DataTestData.TEST_SWEETMODEL.id).subscribe(testSubscriber)
        testSubscriber.await()

        testSubscriber.assertComplete()
        testSubscriber.assertValue(DataTestData.TEST_SWEETMODEL)
    }

    @Test
    fun shouldGetSweetById_noItemFound() {
        db.getSweetById(DataTestData.TEST_SWEETMODEL.id).subscribe(testSubscriber)
        testSubscriber.assertError { it is NullPointerException }
    }

    @Test
    fun searchEmpty_returnsAll() {
        db.addSweets(DataTestData.TEST_LIST_SWEETMODELS).blockingAwait()

        db.search("").subscribe(testSubscriber)
        testSubscriber.await()

        testSubscriber.assertComplete()
        testSubscriber.assertValue(DataTestData.TEST_LIST_SWEETMODELS)
    }

    @Test
    fun searchCorrectName_returnsOne() {
        db.addSweets(DataTestData.TEST_LIST_SWEETMODELS).blockingAwait()

        db.search(DataTestData.TEST_SWEETMODEL.name).subscribe(testSubscriber)
        testSubscriber.await()

        testSubscriber.assertComplete()
        testSubscriber.assertValue(listOf(DataTestData.TEST_SWEETMODEL))
    }

    @Test
    fun searchIncorrectName_returnsNone() {
        db.addSweets(DataTestData.TEST_LIST_SWEETMODELS).blockingAwait()

        db.search(TestData.TEST_SWEET_NAME_SEARCH).subscribe(testSubscriber)
        testSubscriber.await()

        testSubscriber.assertComplete()
        testSubscriber.assertValue(emptyList<SweetDb>())
    }

    @Test
    fun shouldGetLastUpdateTimeStamp() {
        db.addSweets(DataTestData.TEST_LIST_SWEETMODELS).blockingAwait()

        db.getLastUpdateTimeStamp().subscribe(testSubscriber)

        testSubscriber.assertResultValue(DataTestData.TEST_SWEETMODEL2.addedTimestamp)
    }
}