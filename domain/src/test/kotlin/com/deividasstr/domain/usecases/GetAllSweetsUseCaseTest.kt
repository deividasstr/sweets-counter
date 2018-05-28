package com.deividasstr.domain.usecases

import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.entities.Sweet
import com.deividasstr.domain.repositories.SweetsRepo
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetAllSweetsUseCaseTest {

    private lateinit var repo: SweetsRepo
    private lateinit var useCase: GetAllSweetsUseCase
    private lateinit var testSubscriber: TestObserver<List<Sweet>>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repo = Mockito.mock(SweetsRepo::class.java)
        useCase = GetAllSweetsUseCase(repo)
        testSubscriber = TestObserver()
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnAllSweets() {
        Mockito.`when`(repo.getAllSweets())
                .thenReturn(Single.just(TestData.TEST_LIST_SWEETS))

        useCase.execute().subscribeWith(testSubscriber)

        Mockito.verify(repo, Mockito.times(1))
                .getAllSweets()

        Mockito.verifyNoMoreInteractions(repo)

        testSubscriber.assertComplete()
        testSubscriber.assertValue(TestData.TEST_LIST_SWEETS)
    }
}