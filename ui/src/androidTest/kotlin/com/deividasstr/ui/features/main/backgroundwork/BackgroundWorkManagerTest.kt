package com.deividasstr.ui.features.main.backgroundwork

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.testing.WorkManagerTestInitHelper
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.domain.repositories.FactRepo
import com.deividasstr.domain.repositories.PrefsRepo
import com.deividasstr.domain.repositories.SweetsRepo
import com.deividasstr.ui.R
import com.deividasstr.ui.utils.AndroidTest
import com.deividasstr.ui.utils.TestVals
import com.deividasstr.ui.utils.di.TestAppComponent
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.willReturn
import io.reactivex.Completable
import it.cosenonjaviste.daggermock.DaggerMockRule
import javax.inject.Inject
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class BackgroundWorkManagerTest : AndroidTest() {

    private lateinit var backgroundWorkManager: BackgroundWorkManager
    private lateinit var server: MockWebServer

    @get:Rule
    val daggerRule: DaggerMockRule<TestAppComponent> = daggerMockRule()

    @get:Rule
    val instantLiveData = InstantTaskExecutorRule()

    @get:Rule
    val instantRx = AsyncTaskSchedulerRule()

    @Mock
    lateinit var prefsRepo: PrefsRepo

    @Mock
    lateinit var sharedPrefs: SharedPrefs

    @Inject
    lateinit var sweetsRepo: SweetsRepo

    @Inject
    lateinit var factsRepo: FactRepo

    @Inject
    lateinit var workerFactory: WorkersFactory

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()

        TestVals.mockUrl = server.url("/api/").toString()

        println("url ${TestVals.mockUrl}")

        app.appComponent.inject(this)

        val configuration = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(app, configuration)

        backgroundWorkManager = BackgroundWorkManager()
    }

    @Test
    fun downloadSweetsAndSaveDownloadDate() {
        enqueueJsonToResponse(R.raw.test_sweet_response)

        given { sharedPrefs.sweetsUpdatedDate } willReturn { 1 }
        given { prefsRepo.saveSweetsDownloadTime() } willReturn { Completable.complete() }

        val downloadWorkId = backgroundWorkManager.downloadSweetsAndSaveDownloadDate()
        WorkManagerTestInitHelper.getTestDriver().setAllConstraintsMet(downloadWorkId)

        sweetsRepo.getAllSweets()
            .test()
            .await()
            .assertNoErrors()
            .assertComplete()
            .assertValue {
                println("items $it")
                it.size == 9
            }

        verify(prefsRepo, times(1)).saveSweetsDownloadTime()
        verifyNoMoreInteractions(prefsRepo)
    }

    @Test
    fun downloadFactsAndSaveDownloadDate() {
        enqueueJsonToResponse(R.raw.test_fact_response)

        given { prefsRepo.saveFactsDownloadTime() } willReturn { Completable.complete() }
        given { sharedPrefs.factsUpdatedDate } willReturn { 1 }

        val downloadWorkId = backgroundWorkManager.downloadFactsAndSaveDownloadDate()
        WorkManagerTestInitHelper.getTestDriver().setAllConstraintsMet(downloadWorkId)

        factsRepo.getRandomFact(0)
            .test()
            .await()
            .assertNoErrors()
            .assertComplete()
            .assertValueCount(1)

        verify(prefsRepo, times(1)).saveFactsDownloadTime()
        verifyNoMoreInteractions(prefsRepo)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun enqueueJsonToResponse(jsonRes: Int) {
        val json = InstrumentationRegistry.getTargetContext().resources
            .openRawResource(jsonRes)
        server.enqueue(MockResponse().setResponseCode(200).setBody(Buffer().readFrom(json)))
        println("resp ${Buffer().readFrom(json)}")
    }
}
