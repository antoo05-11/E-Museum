package com.example.e_museum.view_controller.fragments.fragments_dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

import androidx.fragment.app.DialogFragment
import com.example.e_museum.R
import com.example.e_museum.view_controller.activities.InsideMuseumActivity
import com.example.e_museum.entities.Museum

class MuseumConfirmDialogFragment(private val activity: Activity, private val museum: Museum) :
    DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)

        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_museum_confirm, null)
        builder.setView(dialogView)


        val cancelButton = dialogView.findViewById<Button>(R.id.cancel_button)
        val confirmButton = dialogView.findViewById<Button>(R.id.confirm_button)
        cancelButton.setOnClickListener {
            dialog!!.dismiss()
        }

        val confirmTextView = dialogView.findViewById<TextView>(R.id.confirm_text_view);
        confirmTextView.text =
            String.format(confirmTextView.text.toString() + " " + museum.name + " chứ?")

        confirmButton.setOnClickListener {
            val intent =
                Intent(activity.applicationContext, com.example.e_museum.view_controller.activities.InsideMuseumActivity::class.java).apply {
                    putExtra("museum", museum)
                }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
            dialog?.dismiss()
        }

        return builder.create().apply {
            window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 30))
        }
    }
}