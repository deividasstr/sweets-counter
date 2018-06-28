package com.deividasstr.ui.features.sweetsearchlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.deividasstr.ui.R
import com.deividasstr.ui.base.models.SweetUi
import kotlinx.android.synthetic.main.sweet_list_item.view.*
import kotlin.math.roundToInt

class SweetsSearchAdapter :
    PagedListAdapter<SweetUi, SweetsSearchAdapter.SweetViewHolder>(DIFF_CALLBACK) {
    var clickListener: (Long) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SweetViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sweet_list_item, parent, false)
        return SweetViewHolder(view)
    }

    override fun onBindViewHolder(holder: SweetViewHolder, position: Int) {
        val concert = getItem(position)
        if (concert != null) {
            holder.bind(concert, clickListener)
        } /*else {
            // Null defines a placeholder item - PagedListAdapter automatically
            // invalidates this row when the actual object is loaded from the
            // database.
            holder.clear()
        }*/
    }

    class SweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sweet: SweetUi, clickListener: (Long) -> Unit) {
            with(itemView) {
                candy_name.text = sweet.name
                candy_cals.text =
                    context.getString(R.string.cals_per_100g, sweet.calsPer100.roundToInt())
                setOnClickListener { clickListener(sweet.id) }
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