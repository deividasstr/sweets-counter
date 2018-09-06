package com.deividasstr.ui.features.sweetsearchlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.ui.base.models.SweetUiDiffCallback
import com.deividasstr.ui.databinding.ListItemSweetBinding

class SweetsSearchAdapter :
    PagedListAdapter<SweetUi, SweetsSearchAdapter.SweetViewHolder>(SweetUiDiffCallback()) {

    var clickListener: (SweetUi, TextView) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SweetViewHolder {
        return SweetViewHolder(
            ListItemSweetBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SweetViewHolder, position: Int) {
        val sweet = getItem(position)
        sweet?.let {
            holder.bind(sweet, clickListener)
        }
    }

    class SweetViewHolder(private val binding: ListItemSweetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SweetUi, listener: (SweetUi, TextView) -> Unit) {
            with(binding) {
                ViewCompat.setTransitionName(candyName, item.name)
                sweet = item
                clickListener = View.OnClickListener { listener(item, candyName) }
            }
        }
    }
}