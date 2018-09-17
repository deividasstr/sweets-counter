package com.deividasstr.ui.features.consumedsweethistory

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.crashlytics.android.Crashlytics
import com.deividasstr.data.utils.DebugOpenClass
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.framework.base.BaseFragment
import com.deividasstr.ui.base.framework.extensions.observe
import com.deividasstr.ui.databinding.FragmentConsumedSweetHistoryBinding
import javax.inject.Inject

@DebugOpenClass
class ConsumedSweetHistoryFragment :
    BaseFragment<FragmentConsumedSweetHistoryBinding, ConsumedSweetHistoryViewModel>() {

    @Inject
    lateinit var dateTimeHandler: DateTimeHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()
        observe(viewModel.sweetCells, ::addSweetsToAdapter)
    }

    private fun setRecycler() {
        val adapter = ConsumedSweetsHeaderAdapter(dateTimeHandler, layoutInflater)
        binding.consumedSweetRecycler.setHasFixedSize(true)
        binding.consumedSweetRecycler.setSectionHeader(adapter)
    }

    private fun addSweetsToAdapter(sweetCells: List<ConsumedSweetCell>?) {
        if (sweetCells!!.isEmpty()) {
            binding.consumedSweetRecycler.addCells(emptyList())
            binding.consumedSweetRecycler.adapter?.notifyDataSetChanged()
        } else {
            binding.consumedSweetRecycler.addCells(sweetCells)
        }
    }

    private fun navigateToSearch() {
        try {
            view?.findNavController()?.navigate(R.id.action_history_to_search)
        } catch (e: Exception) {
            e.printStackTrace()
            Crashlytics.logException(e)
        }
    }

    override fun getViewModelClass(): Class<ConsumedSweetHistoryViewModel> =
        ConsumedSweetHistoryViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_consumed_sweet_history

    override val fabSetter: FabSetter? =
        FabSetter(R.drawable.ic_add_white_24dp) { navigateToSearch() }
}