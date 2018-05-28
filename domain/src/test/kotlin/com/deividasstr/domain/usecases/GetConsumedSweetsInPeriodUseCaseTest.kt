package com.deividasstr.domain.usecases

import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.common.TestData.Companion.TEST_LIST_CONSUMED_SWEETS_EMPTY
import com.deividasstr.domain.entities.ConsumedSweet
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetConsumedSweetsInPeriodUseCaseTest {

    private lateinit var repo: ConsumedSweetsRepo
    private lateinit var useCase: GetConsumedSweetsInPeriodUseCase
    private lateinit var testSubscriber: TestObserver<List<ConsumedSweet>>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repo = Mockito.mock(ConsumedSweetsRepo::class.java)
        useCase = GetConsumedSweetsInPeriodUseCase(repo)
        testSubscriber = TestObserver()
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnConsumedSweetsInPeriod() {
        Mockito.`when`(repo.getConsumedSweetsByPeriod(TestData.TEST_DATERANGE))
                .thenReturn(Single.just(TestData.TEST_LIST_CONSUMED_SWEETS))

        useCase.execute(TestData.TEST_DATERANGE).subscribeWith(testSubscriber)

        Mockito.verify(repo, Mockito.times(1))
                .getConsumedSweetsByPeriod(TestData.TEST_DATERANGE)

        Mockito.verifyNoMoreInteractions(repo)

        testSubscriber.assertComplete()
        testSubscriber.assertValue(TestData.TEST_LIST_CONSUMED_SWEETS)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnNoConsumedSweetsInPeriod() {
        Mockito.`when`(repo.getConsumedSweetsByPeriod(TestData.TEST_DATERANGE))
                .thenReturn(Single.just(TEST_LIST_CONSUMED_SWEETS_EMPTY))

        useCase.execute(TestData.TEST_DATERANGE).subscribeWith(testSubscriber)

        Mockito.verify(repo, Mockito.times(1))
                .getConsumedSweetsByPeriod(TestData.TEST_DATERANGE)

        Mockito.verifyNoMoreInteractions(repo)

        testSubscriber.assertComplete()
        testSubscriber.assertValue(TEST_LIST_CONSUMED_SWEETS_EMPTY)
    }
}