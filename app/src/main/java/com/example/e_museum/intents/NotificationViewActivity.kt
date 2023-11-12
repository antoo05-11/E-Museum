package com.example.e_museum.intents

//noinspection SuspiciousImport
import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.e_museum.SQLConnection
import com.example.e_museum.databinding.ActivityViewNotificationBinding
import com.squareup.picasso.Picasso

class NotificationViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewNotificationBinding;

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewNotificationBinding.inflate(layoutInflater)

        binding.loadingNotificationProgressBar.isVisible = true
        binding.notificationGeneralBox.isVisible = false

        supportActionBar?.title = "Thông báo sự kiện"
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
                    binding.eventDateTv.text = normalizeDate(
                        resultSet.getDate("dateStart").toString()
                    ) + " đến " + normalizeDate(resultSet.getDate("dateEnd").toString())
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

        setContentView(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun normalizeDate(dateGot: String): String {
        val dates = dateGot.split('-')
        return dates[2] + "/" + dates[1] + "/" + dates[0]
    }
}