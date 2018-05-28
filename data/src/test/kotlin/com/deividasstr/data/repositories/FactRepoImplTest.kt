package com.deividasstr.data.repositories

import com.deividasstr.data.DataTestData
import com.deividasstr.data.networking.services.FactsService
import com.deividasstr.data.store.daos.FactsDao
import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.common.assertResultValue
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class FactRepoImplTest : UnitTest() {

    private lateinit var factRepo: FactRepoImpl
    private lateinit var testSubscriber: TestObserver<Any>

    @Mock private lateinit var factsDb: FactsDao
    @Mock private lateinit var factsService: FactsService

    @Before
    fun setUp() {
        factRepo = FactRepoImpl(factsDb, factsService)
        testSubscriber = TestObserver()
    }

    @Test
    fun shouldGetRandomFact() {
        given { factsDb.getRandomFact(1) }
                .willReturn(Single.just(DataTestData.TEST_FACTMODEL_1))

        val fact = factRepo.getRandomFact(1)
        fact.subscribe(testSubscriber)

        testSubscriber.assertResultValue(TestData.TEST_FACT_1)
    }

    @Test
    fun shouldDownloadAllFactsAndSave() {
        given { factsService.getAllFacts() }.willReturn(Single.just(DataTestData.TEST_FACT_LIST))
        given { factsDb.addFacts(any()) }.willReturn(Completable.complete())

        factRepo.downloadAllFactsAndSave().subscribe(testSubscriber)

        testSubscriber.assertComplete()
        verify(factsDb, Mockito.times(1)).addFacts(DataTestData.TEST_FACT_LIST)
        verifyNoMoreInteractions(factsDb)
    }

    @Test
    fun shouldDownloadNewFactsAndSave() {
        given { factsService.getNewFacts(any()) }.willReturn(Single.just(DataTestData.TEST_FACT_LIST))
        given { factsDb.addFacts(any()) }.willReturn(Completable.complete())
        given { factsDb.getLastUpdateTimeStamp() }.willReturn(Single.just(3))

        factRepo.downloadNewFactsAndSave().subscribe(testSubscriber)

        testSubscriber.assertComplete()

        verify(factsDb, Mockito.times(1)).addFacts(DataTestData.TEST_FACT_LIST)
        verify(factsDb, Mockito.times(1)).getLastUpdateTimeStamp()

        verifyNoMoreInteractions(factsDb)
    }
}