package com.deividasstr.domain.usecases

import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.repositories.FactRepo
import com.nhaarman.mockito_kotlin.given
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class DownloadAllFactsUseCaseTest : UnitTest() {

    @Mock
    private lateinit var repo: FactRepo
    private lateinit var useCase: DownloadAllFactsUseCase

    @Before
    fun setUp() {
        useCase = DownloadAllFactsUseCase(repo)
    }

    @Test
    @Throws(Exception::class)
    fun shouldAddNewFact() {
        given { repo.downloadAllFactsAndSave() }.willReturn(Completable.complete())
        useCase.execute().test().assertComplete()
    }
}