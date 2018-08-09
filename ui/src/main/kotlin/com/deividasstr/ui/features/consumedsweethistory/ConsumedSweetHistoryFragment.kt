package com.deividasstr.ui.features.consumedsweethistory

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.deividasstr.data.utils.DebugOpenClass
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.databinding.FragmentConsumedSweetHistoryBinding
import org.zakariya.stickyheaders.StickyHeaderLayoutManager
import javax.inject.Inject

@DebugOpenClass
class ConsumedSweetHistoryFragment :
    BaseFragment<FragmentConsumedSweetHistoryBinding, ConsumedSweetHistoryViewModel>() {

    @Inject lateinit var dateTimeHandler: DateTimeHandler

    override fun getViewModelClass(): Class<ConsumedSweetHistoryViewModel> =
        ConsumedSweetHistoryViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_consumed_sweet_history

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ConsumedSweetsHistoryAdapter(dateTimeHandler)

        viewModel.sweetsPair.observe(this, Observer {
            adapter.setSweets(it.first, it.second)
        })

        setRecyclerView(adapter)

        binding.periodDetailsFab.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_consumedSweetHistoryFragment_to_sweetsSearchListFragment)
        }
    }

    private fun setRecyclerView(adapter: ConsumedSweetsHistoryAdapter) {
        with(binding.consumedSweetRecycler) {
            setElevationToHeader(layoutManager as StickyHeaderLayoutManager)
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    private fun setElevationToHeader(layoutManager: StickyHeaderLayoutManager) {
        layoutManager.headerPositionChangedCallback = StickyHeaderLayoutManager
            .HeaderPositionChangedCallback { _, header, _, newPosition ->
                val elevated = newPosition == StickyHeaderLayoutManager.HeaderPosition.STICKY
                header.elevation = (if (elevated) 8 else 0).toFloat()
            }
    }
}