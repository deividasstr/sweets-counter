package com.deividasstr.domain.usecases

import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.deividasstr.domain.utils.TestData
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetAllConsumedSweetsCalsUseCaseTest {

    private lateinit var repo: ConsumedSweetsRepo
    private lateinit var useCase: GetAllConsumedSweetsCalsUseCase
    private lateinit var testSubscriber: TestObserver<Int>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repo = Mockito.mock(ConsumedSweetsRepo::class.java)
        useCase = GetAllConsumedSweetsCalsUseCase(repo)
        testSubscriber = TestObserver()
    }

    @Test
    @Throws(Exception::class)
    fun shouldGetAllConsumedSweetsCals() {
        Mockito.`when`(repo.getTotalCalsConsumed()).thenReturn(Single.just(TestData.TEST_TOTAL_CALS))

        useCase.execute().subscribeWith(testSubscriber)

        Mockito.verify(repo, Mockito.times(1)).getTotalCalsConsumed()
        Mockito.verifyNoMoreInteractions(repo)

        testSubscriber.assertComplete()
        testSubscriber.assertValue(TestData.TEST_TOTAL_CALS)
    }
}