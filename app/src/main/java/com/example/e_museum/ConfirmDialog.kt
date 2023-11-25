package com.example.e_museum

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.e_museum.activities.InsideMuseumActivity

class ConfirmDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.custom_alert_dialog)
            builder.setMessage(R.string.dialog_choose_museum)
                .setPositiveButton(
                    R.string.go_to_museum
                ) { dialog, id ->
                    val intent = Intent(this.activity?.applicationContext, InsideMuseumActivity::class.java).apply {
                        putExtra(EXTRA_MESSAGE, "hello")
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent)
                }
                .setNegativeButton(
                    R.string.go_back
                ) { dialog, id ->
                }
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
