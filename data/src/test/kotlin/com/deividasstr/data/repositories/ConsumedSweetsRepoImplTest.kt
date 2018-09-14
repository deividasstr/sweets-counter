package com.deividasstr.data.repositories

import com.deividasstr.data.DataTestData
import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.models.toConsumedSweet
import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.willReturn
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class ConsumedSweetsRepoImplTest : UnitTest() {

    private lateinit var sweetsRepo: ConsumedSweetsRepo
    private lateinit var testSubscriber: TestObserver<Any>

    @Mock
    private lateinit var db: ConsumedSweetsDao

    @Before
    fun setUp() {
        sweetsRepo = ConsumedSweetsRepoImpl(db)
        testSubscriber = TestObserver()
    }

    @Test
    fun shouldAddSweet() {
        val sweet = DataTestData.TEST_CONSUMED_SWEETMODEL
        sweet.sweet.target = DataTestData.TEST_SWEETMODEL

        given { db.addSweet(sweet) }.willReturn(Completable.complete())

        sweetsRepo.addSweet(sweet.toConsumedSweet()).subscribe(testSubscriber)

        testSubscriber.await()
        testSubscriber.assertComplete()
    }

    @Test
    fun shouldReturnAllAddedSweets() {
        val sweet = DataTestData.TEST_CONSUMED_SWEETMODEL
        sweet.sweet.target = DataTestData.TEST_SWEETMODEL

        val sweet2 = DataTestData.TEST_CONSUMED_SWEETMODEL2
        sweet2.sweet.target = DataTestData.TEST_SWEETMODEL2

        given { db.getAllConsumedSweets() } willReturn { Single.just(listOf(sweet, sweet2)) }

        sweetsRepo.getAllConsumedSweets().test()
            .assertValue(listOf(sweet.toConsumedSweet(), sweet2.toConsumedSweet()))
    }
}