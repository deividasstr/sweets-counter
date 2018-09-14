package com.deividasstr.ui.features.sweetdetails

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.deividasstr.ui.R

class RatingInfoDialogFragment : DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val inflater = activity!!.layoutInflater
        builder.setView(inflater.inflate(R.layout.dialog_sweet_rating, null))
            .setTitle(R.string.sweet_ratings_title)
            .setMessage(R.string.sweet_ratings_message)
            .setNegativeButton(R.string.got_it) { dialog, _ ->
                dialog.cancel()
            }
        return builder.create()
    }
}
