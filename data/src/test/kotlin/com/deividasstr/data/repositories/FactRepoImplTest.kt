package com.deividasstr.data.repositories

import com.deividasstr.data.DataTestData
import com.deividasstr.data.networking.services.FactsService
import com.deividasstr.data.store.daos.FactsDao
import com.deividasstr.domain.monads.Either
import com.deividasstr.testutils.TestData
import com.deividasstr.testutils.UnitTest
import com.deividasstr.testutils.coGiven
import com.deividasstr.testutils.runBlock
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class FactRepoImplTest : UnitTest() {

    private lateinit var factRepo: FactRepoImpl

    @Mock
    private lateinit var factsDb: FactsDao

    @Mock
    private lateinit var factsService: FactsService

    @Before
    fun setUp() {
        factRepo = FactRepoImpl(factsDb, factsService)
    }

    @Test
    fun shouldGetRandomFact() = runBlock {
        coGiven { factsDb.getFact(1) }.willReturn(Either.Right(DataTestData.TEST_FACTMODEL_1))
        factRepo.getRandomFact(1).getValue() shouldEqual TestData.TEST_FACT_1
    }

    @Test
    fun shouldDownloadAllFactsAndSave() = runBlock {
        coGiven { factsService.getAllFacts() }.willReturn(Either.Right(DataTestData.TEST_FACT_LIST))
        coGiven { factsDb.addFacts(any()) }.willReturn(Either.Right(Either.None()))

        factRepo.downloadAllFactsAndSave() shouldEqual Either.Right(Either.None())

        verify(factsDb).addFacts(DataTestData.TEST_FACT_LIST)
        verifyNoMoreInteractions(factsDb)
    }
}
