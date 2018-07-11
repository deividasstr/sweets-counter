package com.deividasstr.ui.features.main.backgroundwork

import androidx.test.InstrumentationRegistry
import androidx.work.test.WorkManagerTestInitHelper
import com.deividasstr.domain.repositories.PrefsRepo
import com.deividasstr.domain.repositories.SweetsRepo
import com.deividasstr.ui.R
import com.deividasstr.ui.utils.AndroidTest
import com.deividasstr.ui.utils.TestVals
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.willReturn
import io.reactivex.Completable
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.InputStream
import javax.inject.Inject

class BackgroundWorkManagerTest : AndroidTest() {

    private lateinit var backgroundWorkManager: BackgroundWorkManager
    private lateinit var server: MockWebServer

    @Inject
    lateinit var prefsRepo: PrefsRepo

    @Inject
    lateinit var sweetsRepo: SweetsRepo

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()

        TestVals.mockUrl = server.url("/api/")

        backgroundWorkManager = BackgroundWorkManager()
        app.appComponent.inject(this)
    }

    @Test
    fun downloadSweetsAndSaveDownloadDate() {
        val json: InputStream = InstrumentationRegistry.getTargetContext()
            .resources.openRawResource(R.raw.test_sweet_response)
        server.enqueue(
            MockResponse().setResponseCode(200).setBody(Buffer().readFrom(json))
        )

        given { prefsRepo.saveSweetsDownloadTime() } willReturn { Completable.complete() }

        WorkManagerTestInitHelper.initializeTestWorkManager(app)
        val downloadWorkId = backgroundWorkManager.downloadSweetsAndSaveDownloadDate()
        WorkManagerTestInitHelper.getTestDriver().setAllConstraintsMet(downloadWorkId)

        verify(prefsRepo, times(1)).saveSweetsDownloadTime()
        verifyNoMoreInteractions(prefsRepo)

        assertEquals(9, sweetsRepo.getAllSweets().blockingGet().size)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}