package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.Fact
import com.deividasstr.domain.repositories.FactRepo
import com.deividasstr.domain.common.TestData
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetRandomFactUseCaseTest {

    private lateinit var repo: FactRepo
    private lateinit var useCase: GetRandomFactUseCase
    private lateinit var testSubscriber: TestObserver<Fact>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repo = Mockito.mock(FactRepo::class.java)
        useCase = GetRandomFactUseCase(repo)
        testSubscriber = TestObserver()
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnFact() {
        Mockito.`when`(repo.getRandomFact(TestData.TEST_ID))
                .thenReturn(Single.just(TestData.TEST_FACT_1))

        useCase.execute(TestData.TEST_ID).subscribeWith(testSubscriber)

        Mockito.verify(repo, Mockito.times(1))
                .getRandomFact(TestData.TEST_ID)

        Mockito.verifyNoMoreInteractions(repo)

        testSubscriber.assertComplete()
        testSubscriber.assertValue(TestData.TEST_FACT_1)
    }
}