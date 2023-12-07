package com.example.e_museum.view_controller.fragments.fragments_dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.widget.Button

import androidx.fragment.app.DialogFragment
import com.example.e_museum.R

class DataDenyConfirmDialogFragment(private val activity: Activity) :
    DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)

        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_data_deny_confirm, null)
        builder.setView(dialogView)

        val cancelButton = dialogView.findViewById<Button>(R.id.cancel_button)
        cancelButton.setOnClickListener {
            dialog!!.dismiss()
        }

        return builder.create().apply {
            window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 30))
        }
    }
}