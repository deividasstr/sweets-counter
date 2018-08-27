package com.deividasstr.ui.features.consumedsweethistory

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.deividasstr.data.utils.DebugOpenClass
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.ui.databinding.FragmentConsumedSweetHistoryBinding
import javax.inject.Inject

@DebugOpenClass
class ConsumedSweetHistoryFragment :
    BaseFragment<FragmentConsumedSweetHistoryBinding, ConsumedSweetHistoryViewModel>() {

    @Inject
    lateinit var dateTimeHandler: DateTimeHandler

    override fun getViewModelClass(): Class<ConsumedSweetHistoryViewModel> =
        ConsumedSweetHistoryViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_consumed_sweet_history

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ConsumedSweetsHeaderAdapter(
            dateTimeHandler,
            layoutInflater)
        binding.consumedSweetRecycler.setSectionHeader(adapter)

        viewModel.sweetsPair.observe(this, Observer { pairs ->
            if (pairs.first.isEmpty()) {
                binding.consumedSweetRecycler.addCells(emptyList())
                binding.consumedSweetRecycler.adapter?.notifyDataSetChanged()
            } else {
                val cells = getCells(pairs)
                binding.consumedSweetRecycler.addCells(cells)
            }
        })

        binding.consumedSweetRecycler.setHasFixedSize(true)
        binding.periodDetailsFab.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_consumedSweetHistoryFragment_to_sweetsSearchListFragment)
        }
    }

    private fun getCells(allSweets: Pair<List<ConsumedSweetUi>, List<SweetUi>>): List<ConsumedSweetCell> {
        return allSweets.first.map { consumedSweet ->
            val sweet = allSweets.second.find { it.id == consumedSweet.sweetId.toLong() }!!
            val combinedSweet = CombinedSweet(consumedSweet, sweet)
            ConsumedSweetCell(combinedSweet, dateTimeHandler)
        }
    }
}