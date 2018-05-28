package com.deividasstr.data.networking.services

import com.deividasstr.data.DataTestData
import com.deividasstr.data.networking.apis.FactsApi
import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.common.assertResultValue
import com.nhaarman.mockito_kotlin.given
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class FactsServiceTest : UnitTest() {

    private lateinit var factService: FactsService
    private lateinit var testSubscriber: TestObserver<Any>

    @Mock private lateinit var factsApi: FactsApi

    @Before
    fun setUp() {
        factService = FactsService(factsApi)
        testSubscriber = TestObserver()
    }

    @Test
    fun shouldGetAllFacts() {
        given { factsApi.getAllFacts() }
            .willReturn(Single.just(DataTestData.TEST_RESPONSE_FACT_LIST))

        factService.getAllFacts().subscribe(testSubscriber)

        testSubscriber.assertResultValue(DataTestData.TEST_FACT_LIST)
    }

    @Test
    fun shouldGetNewFacts() {
        given { factsApi.getNewFacts(DataTestData.TEST_TIMESTAMP) }
            .willReturn(Single.just(DataTestData.TEST_RESPONSE_FACT_LIST))

        factService.getNewFacts(DataTestData.TEST_TIMESTAMP).subscribe(testSubscriber)

        testSubscriber.assertResultValue(DataTestData.TEST_FACT_LIST)
    }
}