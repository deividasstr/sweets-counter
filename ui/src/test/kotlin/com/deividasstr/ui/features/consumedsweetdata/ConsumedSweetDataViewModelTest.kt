package com.deividasstr.ui.features.consumedsweetdata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.enums.Periods
import com.deividasstr.domain.usecases.GetAllConsumedSweetsUseCase
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.utils.AsyncTaskSchedulerRule
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.then
import com.nhaarman.mockito_kotlin.willReturn
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class ConsumedSweetDataViewModelTest : UnitTest() {

    private lateinit var viewModel: ConsumedSweetDataViewModel

    @get:Rule
    val instantLiveData = InstantTaskExecutorRule()

    @get:Rule
    val instantRx = AsyncTaskSchedulerRule()

    @Mock
    lateinit var getAllConsumedSweetsUseCase: GetAllConsumedSweetsUseCase

    // Sweets on days - 2 on current day, 1 day before yesterday, 1 last week, 1 last month, 1 last year
    @Before
    fun setUp() {
        given { getAllConsumedSweetsUseCase.execute() } willReturn {
            Single.just(TestData.TEST_LIST_CONSUMED_SWEETS3)
        }

        viewModel = ConsumedSweetDataViewModel(getAllConsumedSweetsUseCase)
    }

    @Test
    fun sweetsPair() {
        val consumedSweetUi = TestData.TEST_LIST_CONSUMED_SWEETS3.map { ConsumedSweetUi(it) }

        val observer: Observer<List<ConsumedSweetUi>> = mock()
        viewModel.consumedSweets.observeForever(observer)
        then(observer).should().onChanged(consumedSweetUi)
    }

    @Test
    fun currentPeriod_shouldChange() {
        val observer: Observer<Periods> = mock()
        viewModel.currentPeriod.observeForever(observer)
        then(observer).should().onChanged(Periods.WEEK)
        viewModel.changePeriod(1) // Clicking MONTH period
        then(observer).should().onChanged(Periods.MONTH)
    }

    @Test
    fun currentPeriod_shouldNotChange() {
        val observer: Observer<Periods> = mock()
        viewModel.currentPeriod.observeForever(observer)
        then(observer).should().onChanged(Periods.WEEK)
        viewModel.changePeriod(0) // Clicking WEEK period
        then(observer).shouldHaveNoMoreInteractions()
    }
}