package com.deividasstr.ui.base.models

import androidx.recyclerview.widget.DiffUtil

class SweetUiDiffCallback : DiffUtil.ItemCallback<SweetUi>() {
    override fun areItemsTheSame(oldItem: SweetUi, newItem: SweetUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SweetUi, newItem: SweetUi): Boolean {
        return oldItem.id == newItem.id
    }
}
