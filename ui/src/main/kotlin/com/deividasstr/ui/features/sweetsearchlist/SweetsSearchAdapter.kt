package com.deividasstr.ui.features.sweetsearchlist

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.deividasstr.ui.R
import com.deividasstr.ui.base.models.SweetUi
import kotlinx.android.synthetic.main.sweet_list_item.view.*
import kotlin.math.roundToInt

class SweetsSearchAdapter :
    PagedListAdapter<SweetUi, SweetsSearchAdapter.SweetViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SweetViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sweet_list_item, parent, false)
        return SweetViewHolder(view)
    }

    override fun onBindViewHolder(holder: SweetViewHolder, position: Int) {
        val concert = getItem(position)
        if (concert != null) {
            holder.bind(concert)
        } /*else {
            // Null defines a placeholder item - PagedListAdapter automatically
            // invalidates this row when the actual object is loaded from the
            // database.
            holder.clear()
        }*/
    }

    class SweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sweet: SweetUi) {
            with(itemView) {
                candy_name.text = sweet.name
                candy_cals.text =
                    context.getString(R.string.cals_per_100g, sweet.calsPer100.roundToInt())
            }
        }

        /*fun clear() {
        }*/
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