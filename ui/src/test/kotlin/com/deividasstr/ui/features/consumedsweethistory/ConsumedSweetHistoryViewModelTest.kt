package com.deividasstr.ui.features.consumedsweethistory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.usecases.GetAllConsumedSweetsUseCase
import com.deividasstr.domain.usecases.GetSweetsByIdsUseCase
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.utils.AsyncTaskSchedulerRule
import com.deividasstr.utils.UiTestData
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.then
import com.nhaarman.mockito_kotlin.willReturn
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class ConsumedSweetHistoryViewModelTest : UnitTest() {

    private lateinit var viewModel: ConsumedSweetHistoryViewModel

    @get:Rule
    val instantLiveData = InstantTaskExecutorRule()
    @get:Rule
    val instantRx = AsyncTaskSchedulerRule()

    @Mock
    lateinit var getAllConsumedSweetsUseCase: GetAllConsumedSweetsUseCase
    @Mock
    lateinit var getSweetsByIdsUseCase: GetSweetsByIdsUseCase
    @Mock
    lateinit var observer: Observer<Pair<List<ConsumedSweetUi>, List<SweetUi>>>

    @Before
    fun setUp() {
        given { getAllConsumedSweetsUseCase.execute() } willReturn {
            Single.just(TestData.TEST_LIST_CONSUMED_SWEETS2)
        }

        given { getSweetsByIdsUseCase.execute(any()) } willReturn {
            Single.just(TestData.TEST_LIST_SWEETS)
        }

        viewModel =
            ConsumedSweetHistoryViewModel(getAllConsumedSweetsUseCase, getSweetsByIdsUseCase)
    }

    @Test
    fun sweetsPair() {
        val sweets = UiTestData.UI_SWEET_LIST
        val consumedSweets = UiTestData.UI_CONSUMED_SWEET_LIST
        val result = Pair(consumedSweets, sweets)

        viewModel.sweetsPair.observeForever(observer)

        then(observer).should().onChanged(result)
        then(observer).shouldHaveNoMoreInteractions()
    }
}