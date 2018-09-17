package com.deividasstr.domain.usecases

import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.models.Sweet
import com.deividasstr.domain.repositories.SweetsRepo
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetSweetByIdUseCaseTest {

    private lateinit var repo: SweetsRepo
    private lateinit var useCase: GetSweetByIdUseCase
    private lateinit var testSubscriber: TestObserver<Sweet>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repo = Mockito.mock(SweetsRepo::class.java)
        useCase = GetSweetByIdUseCase(repo)
        testSubscriber = TestObserver()
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnSweet() {
        Mockito.`when`(repo.getSweetById(TestData.TEST_ID))
                .thenReturn(Single.just(TestData.TEST_SWEET))

        useCase.execute(TestData.TEST_ID).subscribeWith(testSubscriber)

        Mockito.verify(repo, Mockito.times(1))
                .getSweetById(TestData.TEST_ID)

        Mockito.verifyNoMoreInteractions(repo)

        testSubscriber.assertComplete()
        testSubscriber.assertValue(TestData.TEST_SWEET)
    }
}