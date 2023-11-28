package com.example.e_museum.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button

import androidx.fragment.app.DialogFragment
import com.example.e_museum.R

class CustomConfirmDialog(private val _activity: Activity): DialogFragment() {
    private val activity: Activity = _activity
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)

        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_dialog, null)
        builder.setView(dialogView)
        val cancel_btn = dialogView.findViewById<Button>(R.id.cancel_button)
        val confirm_btn = dialogView.findViewById<Button>(R.id.confirm_button)

        cancel_btn.setOnClickListener{
            dialog!!.dismiss()
        }

        confirm_btn.setOnClickListener {
            val intent =
                Intent(activity, InsideMuseumActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
        }

        return builder.create()
    }
}