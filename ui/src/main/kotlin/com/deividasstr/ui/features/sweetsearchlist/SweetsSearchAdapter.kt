package com.deividasstr.ui.features.sweetsearchlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.deividasstr.ui.R
import com.deividasstr.ui.base.models.SweetUi
import kotlinx.android.synthetic.main.sweet_list_item.view.*

class SweetsSearchAdapter :
    PagedListAdapter<SweetUi, SweetsSearchAdapter.SweetViewHolder>(DIFF_CALLBACK) {

    var clickListener: (SweetUi, TextView) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SweetViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sweet_list_item, parent, false)
        return SweetViewHolder(view)
    }

    override fun onBindViewHolder(holder: SweetViewHolder, position: Int) {
        val concert = getItem(position)
        if (concert != null) {
            holder.bind(concert, clickListener)
        } else {
            holder.clear()
        }
    }

    class SweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sweet: SweetUi, clickListener: (SweetUi, TextView) -> Unit) {
            with(itemView) {
                ViewCompat.setTransitionName(candy_name, sweet.name)
                candy_name.text = sweet.name
                candy_cals.text =
                    context.getString(R.string.cals_per_100g, sweet.calsPer100)
                setOnClickListener { clickListener(sweet, candy_name) }
            }
        }

        fun clear() {
            with(itemView) {
                candy_name.text = ""
                candy_cals.text = ""
                setOnClickListener { }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SweetUi>() {
            override fun areItemsTheSame(oldItem: SweetUi, newItem: SweetUi): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SweetUi, newItem: SweetUi): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}