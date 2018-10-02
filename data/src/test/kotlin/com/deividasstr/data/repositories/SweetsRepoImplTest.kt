package com.deividasstr.data.repositories

import com.deividasstr.data.DataTestData
import com.deividasstr.data.networking.services.SweetsService
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.common.assertResultValue
import com.deividasstr.domain.entities.models.Sweet
import com.deividasstr.domain.repositories.SweetsRepo
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

class SweetsRepoImplTest : UnitTest() {

    private lateinit var sweetsRepo: SweetsRepo
    private lateinit var testSubscriber: TestObserver<Any>

    @Mock private lateinit var sweetsDb: SweetsDao
    @Mock private lateinit var sweetsService: SweetsService
    @Mock private lateinit var sharedPrefs: SharedPrefs

    @Before
    fun setUp() {
        sweetsRepo = SweetsRepoImpl(sweetsDb, sweetsService, sharedPrefs)
        testSubscriber = TestObserver()
    }

    @Test
    fun shouldGetAllSweets() {
        given { sweetsDb.getAllSweets() }.willReturn(Single.just(DataTestData.TEST_LIST_SWEETMODELS))

        sweetsRepo.getAllSweets().subscribe(testSubscriber)

        testSubscriber.assertResultValue(TestData.TEST_LIST_SWEETS)
    }

    @Test
    fun shouldGetSweetById() {
        given { sweetsDb.getSweetById(2) }.willReturn(Single.just(DataTestData.TEST_SWEETMODEL))

        sweetsRepo.getSweetById(2).subscribe(testSubscriber)

        testSubscriber.assertResultValue(TestData.TEST_SWEET)
    }

    @Test
    fun searchEmpty_returnsAll() {
        given { sweetsDb.search("") }.willReturn(Single.just(DataTestData.TEST_LIST_SWEETMODELS))

        sweetsRepo.search("").subscribe(testSubscriber)

        testSubscriber.assertResultValue(TestData.TEST_LIST_SWEETS)
    }

    @Test
    fun shouldSearchCorrectName_returnsOne() {
        given { sweetsDb.search(TestData.TEST_SWEET.name) }
                .willReturn(Single.just(listOf(DataTestData.TEST_SWEETMODEL)))

        sweetsRepo.search(TestData.TEST_SWEET.name).subscribe(testSubscriber)

        testSubscriber.assertResultValue(listOf(TestData.TEST_SWEET))
    }

    @Test
    fun shouldSearchIncorrectName_returnsNone() {
        given { sweetsDb.search(TestData.TEST_SWEET_NAME_SEARCH) }.willReturn(Single.just(emptyList()))

        sweetsRepo.search(TestData.TEST_SWEET_NAME_SEARCH).subscribe(testSubscriber)

        testSubscriber.assertResultValue(emptyList<Sweet>())
    }

    @Test
    fun shouldAddNewSweet() {
        given { sweetsDb.addSweet(DataTestData.TEST_SWEETMODEL) }.willReturn(Completable.complete())
        sweetsRepo.newSweet(TestData.TEST_SWEET).subscribe(testSubscriber)

        testSubscriber.await()
        testSubscriber.assertComplete()
    }

    @Test
    fun shouldDownloadAllSweetsAndSave() {
        given { sweetsService.getAllSweets() }.willReturn(Single.just(DataTestData.TEST_LIST_SWEETMODELS))
        given { sweetsDb.addSweets(any()) }.willReturn(Completable.complete())

        sweetsRepo.downloadAndSaveAllSweets().subscribe(testSubscriber)

        testSubscriber.assertComplete()
        verify(sweetsDb, Mockito.times(1)).addSweets(DataTestData.TEST_LIST_SWEETMODELS)
        verifyNoMoreInteractions(sweetsDb)
    }

    @Test
    fun shouldDownloadNewSweetsAndSave() {
        given { sweetsService.getNewSweets(any()) }.willReturn(Single.just(DataTestData.TEST_LIST_SWEETMODELS))
        given { sweetsDb.addSweets(any()) }.willReturn(Completable.complete())

        sweetsRepo.downloadAndSaveNewSweets().subscribe(testSubscriber)

        testSubscriber.assertComplete()

        verify(sweetsDb, Mockito.times(1)).addSweets(DataTestData.TEST_LIST_SWEETMODELS)

        verifyNoMoreInteractions(sweetsDb)
    }
}