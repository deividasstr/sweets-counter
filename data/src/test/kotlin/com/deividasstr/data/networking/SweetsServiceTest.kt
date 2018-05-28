package com.deividasstr.data.networking

import com.deividasstr.data.DataTestData
import com.deividasstr.data.networking.apis.SweetsApi
import com.deividasstr.data.networking.services.SweetsService
import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.common.assertResultValue
import com.nhaarman.mockito_kotlin.given
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class SweetsServiceTest : UnitTest() {

    private lateinit var sweetsService: SweetsService
    private lateinit var testSubscriber: TestObserver<Any>

    @Mock
    private lateinit var sweetApi: SweetsApi

    @Before
    fun setUp() {
        sweetsService = SweetsService(sweetApi)
        testSubscriber = TestObserver()
    }

    @Test
    fun shouldGetAllSweets() {
        given { sweetApi.getAllSweets() }
            .willReturn(Single.just(DataTestData.TEST_RESPONSE_SWEET_LIST))

        sweetsService.getAllSweets().subscribe(testSubscriber)

        testSubscriber.assertResultValue(DataTestData.TEST_LIST_SWEETMODELS)
    }

    @Test
    fun shouldGetNewSweets() {
        given { sweetApi.getNewSweets(DataTestData.TEST_TIMESTAMP) }
            .willReturn(Single.just(DataTestData.TEST_RESPONSE_SWEET_LIST))

        sweetsService.getNewSweets(DataTestData.TEST_TIMESTAMP).subscribe(testSubscriber)

        testSubscriber.assertResultValue(DataTestData.TEST_LIST_SWEETMODELS)
    }
}