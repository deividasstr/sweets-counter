package com.deividasstr.ui.features.consumedsweethistory

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.crashlytics.android.Crashlytics
import com.deividasstr.data.utils.DebugOpenClass
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.databinding.FragmentConsumedSweetHistoryBinding
import javax.inject.Inject

@DebugOpenClass
class ConsumedSweetHistoryFragment :
    BaseFragment<FragmentConsumedSweetHistoryBinding, ConsumedSweetHistoryViewModel>() {

    override val fabSetter: FabSetter? =
        FabSetter(R.drawable.ic_add_white_24dp) { navigateToSearch() }

    @Inject
    lateinit var dateTimeHandler: DateTimeHandler

    override fun getViewModelClass(): Class<ConsumedSweetHistoryViewModel> =
        ConsumedSweetHistoryViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_consumed_sweet_history

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ConsumedSweetsHeaderAdapter(dateTimeHandler, layoutInflater)
        binding.consumedSweetRecycler.setSectionHeader(adapter)

        viewModel.sweetsPair.observe(this, Observer { cells ->
            if (cells.isEmpty()) {
                binding.consumedSweetRecycler.addCells(emptyList())
                binding.consumedSweetRecycler.adapter?.notifyDataSetChanged()
            } else {
                binding.consumedSweetRecycler.addCells(cells)
            }
        })

        binding.consumedSweetRecycler.setHasFixedSize(true)
    }

    private fun navigateToSearch() {
        try {
            view?.findNavController()
                ?.navigate(R.id.action_consumedSweetHistoryFragment_to_sweetsSearchListFragment)
        } catch (e: Exception) {
            Crashlytics.logException(e)
        }
    }
}