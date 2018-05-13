package com.deividasstr.domain.usecases

import com.deividasstr.domain.repositories.NewSweetsRepo
import com.deividasstr.domain.utils.TestData
import io.reactivex.Completable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class AddNewSweetUseCaseTest {

    private lateinit var repo: NewSweetsRepo
    private lateinit var useCase: AddNewSweetUseCase
    private lateinit var testSubscriber: TestObserver<Any>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repo = Mockito.mock(NewSweetsRepo::class.java)
        useCase = AddNewSweetUseCase(repo)
        testSubscriber = TestObserver()
    }

    @Test
    @Throws(Exception::class)
    fun shouldAddNewSweet() {
        Mockito.`when`(repo.newSweet(TestData.TEST_SWEET)).thenReturn(Completable.complete())

        useCase.execute(TestData.TEST_SWEET).subscribeWith(testSubscriber)

        Mockito.verify(repo, Mockito.times(1)).newSweet(TestData.TEST_SWEET)
        Mockito.verifyNoMoreInteractions(repo)

        testSubscriber.assertComplete()
    }
}