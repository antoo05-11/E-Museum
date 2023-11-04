package com.example.e_museum

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.dialog_choose_museum)
                    .setPositiveButton(R.string.go_to_museum
                    ) { dialog, id ->
                    }
                .setNegativeButton(R.string.go_back
                ) { dialog, id ->
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
