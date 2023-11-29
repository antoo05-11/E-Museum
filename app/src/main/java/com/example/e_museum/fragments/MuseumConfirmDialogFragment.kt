package com.example.e_museum.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.widget.Button

import androidx.fragment.app.DialogFragment
import com.example.e_museum.R
import com.example.e_museum.activities.InsideMuseumActivity
import com.example.e_museum.entities.Museum

class MuseumConfirmDialogFragment(private val _activity: Activity, private val _museum: Museum) :
    DialogFragment() {
    private val activity: Activity = _activity
    private val museum: Museum = _museum

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)

        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_dialog, null)
        builder.setView(dialogView)


        val cancelButton = dialogView.findViewById<Button>(R.id.cancel_button)
        val confirmButton = dialogView.findViewById<Button>(R.id.confirm_button)
        cancelButton.setOnClickListener {
            dialog!!.dismiss()
        }

        confirmButton.setOnClickListener {
            val intent =
                Intent(activity.applicationContext, InsideMuseumActivity::class.java).apply {
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