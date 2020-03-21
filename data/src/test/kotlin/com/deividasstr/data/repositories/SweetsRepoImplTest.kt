package com.deividasstr.data.repositories

import com.deividasstr.data.DataTestData
import com.deividasstr.data.networking.services.SweetsService
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.SweetsRepo
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

class SweetsRepoImplTest : UnitTest() {

    private lateinit var sweetsRepo: SweetsRepo

    @Mock
    private lateinit var sweetsDb: SweetsDao
    @Mock
    private lateinit var sweetsService: SweetsService
    @Mock
    private lateinit var sharedPrefs: SharedPrefs

    @Before
    fun setUp() {
        sweetsRepo = SweetsRepoImpl(sweetsDb, sweetsService, sharedPrefs)
    }

    @Test
    fun shouldGetSweetById() = runBlock {
        coGiven { sweetsDb.getSweetById(2) }
            .willReturn(Either.Right(DataTestData.TEST_SWEETMODEL))

        sweetsRepo.getSweetById(2).getValue() shouldEqual TestData.TEST_SWEET
    }

    @Test
    fun shouldAddNewSweet() = runBlock {
        coGiven { sweetsDb.addSweet(DataTestData.TEST_SWEETMODEL) }
            .willReturn(Either.Right(Either.None()))

        sweetsRepo.newSweet(TestData.TEST_SWEET) shouldEqual Either.Right(Either.None())
    }

    @Test
    fun shouldDownloadAllSweetsAndSave() = runBlock {
        coGiven { sweetsService.getAllSweets() }
            .willReturn(Either.Right(DataTestData.TEST_LIST_SWEETMODELS))
        coGiven { sweetsDb.addSweets(any()) }
            .willReturn(Either.Right(Either.None()))

        sweetsRepo.downloadAndSaveAllSweets() shouldEqual Either.Right(Either.None())

        verify(sweetsDb).addSweets(DataTestData.TEST_LIST_SWEETMODELS)
        verifyNoMoreInteractions(sweetsDb)
    }

    @Test
    fun shouldDownloadNewSweetsAndSave() = runBlock {
        coGiven { sweetsService.getNewSweets(any()) }
            .willReturn(Either.Right(DataTestData.TEST_LIST_SWEETMODELS))
        coGiven { sweetsDb.addSweets(any()) }
            .willReturn(Either.Right(Either.None()))

        sweetsRepo.downloadAndSaveNewSweets() shouldEqual Either.Right(Either.None())
        verify(sweetsDb).addSweets(DataTestData.TEST_LIST_SWEETMODELS)
        verifyNoMoreInteractions(sweetsDb)
    }
}