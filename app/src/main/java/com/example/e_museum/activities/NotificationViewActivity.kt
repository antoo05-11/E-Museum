package com.example.e_museum.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.e_museum.R
import com.example.e_museum.data_fetching.models.Model
import com.example.e_museum.databinding.ActivityViewNotificationBinding
import com.squareup.picasso.Picasso

class NotificationViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loadingNotificationProgressBar.isVisible = true
        binding.notificationGeneralBox.isVisible = false

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
                binding.notificationConditionTv.text = notification.condition
                binding.notIcationContentTv.text = notification.content
                binding.eventDateTv.text = String.format(
                    "%s đến %s",
                    normalizeDate(notification.dateStart.toString()),
                    normalizeDate(notification.dateEnd.toString())
                )
                Picasso.get()
                    .load(
                        String.format(
                            MainActivity.fileServerURL + "notifications/notification_%d_preview_image.png",
                            intent.getIntExtra("notification_id", 1)
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

    private fun normalizeDate(dateGot: String): String {
        val dates = dateGot.split('-')
        return dates[2] + "/" + dates[1] + "/" + dates[0]
    }
}