package com.deividasstr.domain.usecases

import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import io.reactivex.Completable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class AddConsumedSweetUseCaseTest {

    private lateinit var repo: ConsumedSweetsRepo
    private lateinit var useCase: AddConsumedSweetUseCase
    private lateinit var testSubscriber: TestObserver<Any>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repo = Mockito.mock(ConsumedSweetsRepo::class.java)
        useCase = AddConsumedSweetUseCase(repo)
        testSubscriber = TestObserver()
    }

    @Test
    @Throws(Exception::class)
    fun shouldAddConsumedSweet() {
        Mockito.`when`(repo.addSweet(TestData.TEST_CONSUMED_SWEET)).thenReturn(Completable.complete())

        useCase.execute(TestData.TEST_CONSUMED_SWEET).subscribeWith(testSubscriber)

        Mockito.verify(repo, Mockito.times(1)).addSweet(TestData.TEST_CONSUMED_SWEET)
        Mockito.verifyNoMoreInteractions(repo)

        testSubscriber.assertComplete()
    }
}