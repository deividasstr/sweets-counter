package com.deividasstr.domain.usecases

import com.deividasstr.domain.repositories.SweetsRepo
import io.reactivex.Completable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class DownloadAllSweetsUseCaseTest {

    private lateinit var repo: SweetsRepo
    private lateinit var useCase: DownloadAllSweetsUseCase
    private lateinit var testSubscriber: TestObserver<Any>

    @Before
    fun setUp() {
        repo = Mockito.mock(SweetsRepo::class.java)
        useCase = DownloadAllSweetsUseCase(repo)
        testSubscriber = TestObserver()
    }

    @Test
    @Throws(Exception::class)
    fun shouldAddNewSweet() {
        Mockito.`when`(repo.downloadAndSaveAllSweets()).thenReturn(Completable.complete())

        useCase.execute().subscribeWith(testSubscriber)

        Mockito.verify(repo, Mockito.times(1)).downloadAndSaveAllSweets()
        Mockito.verifyNoMoreInteractions(repo)

        testSubscriber.assertComplete()
    }
}