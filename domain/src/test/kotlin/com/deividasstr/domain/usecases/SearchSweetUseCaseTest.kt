package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.models.Sweet
import com.deividasstr.domain.repositories.SweetsRepo
import com.deividasstr.domain.common.TestData
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class SearchSweetUseCaseTest {

    private lateinit var repo: SweetsRepo
    private lateinit var useCase: SearchSweetUseCase
    private lateinit var testSubscriber: TestObserver<List<Sweet>>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repo = Mockito.mock(SweetsRepo::class.java)
        useCase = SearchSweetUseCase(repo)
        testSubscriber = TestObserver()
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnSweets() {
        Mockito.`when`(repo.search(TestData.TEST_SWEET_NAME_SEARCH))
                .thenReturn(Single.just(TestData.TEST_LIST_SWEETS))

        useCase.execute(TestData.TEST_SWEET_NAME_SEARCH).subscribeWith(testSubscriber)

        Mockito.verify(repo, Mockito.times(1))
                .search(TestData.TEST_SWEET_NAME_SEARCH)

        Mockito.verifyNoMoreInteractions(repo)

        testSubscriber.assertComplete()
        testSubscriber.assertValue(TestData.TEST_LIST_SWEETS)
    }
}