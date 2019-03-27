package com.deividasstr.data.networking.services

import com.deividasstr.data.DataTestData
import com.deividasstr.data.networking.apis.FactsApi
import com.deividasstr.data.networking.manager.NetworkManager
import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.utils.coGiven
import com.deividasstr.domain.utils.runBlock
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.willReturn
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Response

class FactsServiceTest : UnitTest() {

    private lateinit var factService: FactsService

    @Mock
    private lateinit var factsApi: FactsApi
    @Mock
    private lateinit var networkManager: NetworkManager

    @Before
    fun setUp() {
        factService = FactsService(factsApi, networkManager)
        given { networkManager.networkAvailable } willReturn { true }
    }

    @Test
    fun shouldGetAllFacts() = runBlock {
        val testVal = DataTestData.TEST_RESPONSE_FACT_LIST

        coGiven { factsApi.getAllFacts() } willReturn { Response.success(testVal) }

        factService.getAllFacts().getValue() shouldEqual DataTestData.TEST_FACT_LIST

        verify(factsApi).getAllFacts()
        verifyNoMoreInteractions(factsApi)
    }

    @Test
    fun shouldGetNewFacts() = runBlock {
        val testVal = DataTestData.TEST_RESPONSE_FACT_LIST
        val testResult = DataTestData.TEST_FACT_LIST

        coGiven { factsApi.getNewFacts(DataTestData.TEST_TIMESTAMP) } willReturn {
            Response.success(testVal)
        }

        factService.getNewFacts(DataTestData.TEST_TIMESTAMP).getValue() shouldEqual testResult

        verify(factsApi).getNewFacts(DataTestData.TEST_TIMESTAMP)
        verifyNoMoreInteractions(factsApi)
    }
}