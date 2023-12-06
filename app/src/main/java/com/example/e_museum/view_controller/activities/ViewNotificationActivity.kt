package com.example.e_museum.view_controller.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.e_museum.R
import com.example.e_museum.data_fetching.models.Model
import com.example.e_museum.databinding.ActivityViewNotificationBinding
import com.example.e_museum.utils.normalizeDate
import com.squareup.picasso.Picasso

class ViewNotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loadingNotificationProgressBar.isVisible = true
        binding.notificationGeneralBox.isVisible = false

        binding.backViewThingButton.setOnClickListener {
            finish()
        }

        supportActionBar?.title = getString(R.string.event_notification)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Thread {
            val notification = Model.getInstance().notificationModel.getNotificationByID(
                intent.getIntExtra(
                    "notification_id",
                    1
                )
            ) ?: return@Thread

            runOnUiThread {
                binding.loadingNotificationProgressBar.isVisible = false
                binding.notificationGeneralBox.isVisible = true

                binding.notificationNameTv.text = notification.name
                if (notification.condition.isNullOrEmpty()) {
                    (binding.conditionContainer.parent as LinearLayout).removeView(binding.conditionContainer)
                } else binding.notificationConditionTv.text = notification.condition
                binding.notIcationContentTv.text = notification.content.replace(
                    "\\n",
                    System.getProperty("line.separator")!! + System.getProperty("line.separator")
                )
                binding.notIcationContentTv.setLineSpacing(10f, 1.1f)
                binding.eventDateTv.text = String.format(
                    "%s đến %s",
                    normalizeDate(notification.dateStart.toString()),
                    normalizeDate(notification.dateEnd.toString())
                )
                Picasso.get()
                    .load(
                        String.format(
                            MainActivity.fileServerURL + "notifications/%d_%d.png",
                            notification.museumID, notification.notificationID
                        )
                    )
                    .fit()
                    .centerInside()
                    .into(binding.notificationMainImage)
            }

        }.start()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}