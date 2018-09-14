package com.deividasstr.domain.usecases

import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.repositories.PrefsRepo
import com.nhaarman.mockito_kotlin.given
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class SaveDownloadSweetDateUseCaseTest : UnitTest() {

    @Mock
    private lateinit var repo: PrefsRepo
    private lateinit var useCase: SaveDownloadSweetDateUseCase

    @Before
    fun setUp() {
        useCase = SaveDownloadSweetDateUseCase(repo)
    }

    @Test
    @Throws(Exception::class)
    fun shouldAddNewFact() {
        given { repo.saveSweetsDownloadTime() }.willReturn(Completable.complete())
        useCase.execute().test().assertComplete()
    }
}