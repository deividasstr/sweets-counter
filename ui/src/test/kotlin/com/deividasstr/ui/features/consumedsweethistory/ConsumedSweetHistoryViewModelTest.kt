package com.deividasstr.ui.features.consumedsweethistory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.entities.DateTimeHandler
import com.deividasstr.domain.entities.enums.MeasurementUnit
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.usecases.GetAllConsumedSweetsUseCase
import com.deividasstr.domain.utils.coGiven
import com.deividasstr.domain.utils.runBlock
import com.deividasstr.utils.UiTestData
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.willReturn
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class ConsumedSweetHistoryViewModelTest : UnitTest() {

    private lateinit var viewModel: ConsumedSweetHistoryViewModel

    @get:Rule
    val instantLiveData = InstantTaskExecutorRule()

    @Mock
    lateinit var getAllConsumedSweetsUseCase: GetAllConsumedSweetsUseCase
    @Mock
    lateinit var dateTimeHandler: DateTimeHandler
    @Mock
    lateinit var sharedPrefs: SharedPrefs

    @Before
    fun setUp() = runBlocking {
        coGiven { getAllConsumedSweetsUseCase.run() } willReturn {
            Either.Right(TestData.TEST_LIST_CONSUMED_SWEETS2)
        }

        given { sharedPrefs.defaultMeasurementUnit } willReturn {
            MeasurementUnit.GRAM
        }

        viewModel =
            ConsumedSweetHistoryViewModel(
                getAllConsumedSweetsUseCase,
                dateTimeHandler,
                sharedPrefs)
    }

    @Test
    fun sweetsPair() = runBlock {
        val consumedSweets = UiTestData.UI_CONSUMED_SWEET_LIST
        val result = listOf(
            ConsumedSweetCell(
                consumedSweets[0],
                dateTimeHandler,
                MeasurementUnit.GRAM),
            ConsumedSweetCell(
                consumedSweets[1],
                dateTimeHandler,
                MeasurementUnit.GRAM)
        )

        viewModel.sweetCells.test().assertValue(result)
    }
}