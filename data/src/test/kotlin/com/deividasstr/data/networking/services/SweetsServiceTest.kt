package com.deividasstr.data.networking.services

import com.deividasstr.data.DataTestData
import com.deividasstr.data.networking.apis.SweetsApi
import com.deividasstr.data.networking.manager.NetworkManager
import com.deividasstr.testutils.UnitTest
import com.deividasstr.testutils.coGiven
import com.deividasstr.testutils.runBlock
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.willReturn
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Response

class SweetsServiceTest : UnitTest() {

    private lateinit var sweetsService: SweetsService

    @Mock
    private lateinit var sweetApi: SweetsApi

    @Mock
    private lateinit var networkManager: NetworkManager

    @Before
    fun setUp() {
        given { networkManager.networkAvailable } willReturn { true }
        sweetsService = SweetsService(sweetApi, networkManager)
    }

    @Test
    fun shouldGetAllSweets() = runBlock {
        val testVal = DataTestData.TEST_RESPONSE_SWEET_LIST
        val testResult = DataTestData.TEST_LIST_SWEETMODELS

        coGiven { sweetApi.getAllSweets() } willReturn {
            Response.success(testVal)
        }

        sweetsService.getAllSweets().getValue() shouldEqual testResult

        verify(sweetApi).getAllSweets()
        verifyNoMoreInteractions(sweetApi)
    }

    @Test
    fun shouldGetNewSweets() = runBlock {
        val testVal = DataTestData.TEST_RESPONSE_SWEET_LIST
        val testResult = DataTestData.TEST_LIST_SWEETMODELS

        coGiven { sweetApi.getNewSweets(DataTestData.TEST_TIMESTAMP) } willReturn {
            Response.success(testVal)
        }

        sweetsService.getNewSweets(DataTestData.TEST_TIMESTAMP).getValue() shouldEqual testResult

        verify(sweetApi).getNewSweets(any())
        verifyNoMoreInteractions(sweetApi)
    }
}
