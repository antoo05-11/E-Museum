package com.example.e_museum.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.e_museum.R
import com.example.e_museum.utils.SQLConnection
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
            val resultSet = SQLConnection.getSqlConnection().getDataQuery(
                String.format(
                    "select * from notifications where notificationID = %d",
                    intent.getIntExtra("notification_id", 1)
                )
            )
            runOnUiThread {
                binding.loadingNotificationProgressBar.isVisible = false
                binding.notificationGeneralBox.isVisible = true

                if (resultSet.next()) {
                    binding.notificationNameTv.text = resultSet.getString("name")
                    binding.notificationConditionTv.text = resultSet.getString("condition")
                    binding.notIcationContentTv.text = resultSet.getString("content")
                    binding.eventDateTv.text = String.format(
                        "%s đến %s",
                        normalizeDate(resultSet.getDate("dateStart").toString()),
                        normalizeDate(resultSet.getDate("dateEnd").toString())
                    )
                    Picasso.get()
                        .load(
                            String.format(
                                "https://muzik-files-server.000webhostapp.com/emuseum/notifications/notification_%d_preview_image.png",
                                intent.getIntExtra("notification_id", 1)
                            )
                        )
                        .fit()
                        .centerInside()
                        .into(binding.notificationMainImage)
                }
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