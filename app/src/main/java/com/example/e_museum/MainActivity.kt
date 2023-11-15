package com.example.e_museum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_museum.databinding.ActivityFindingMuseumBinding
import com.example.e_museum.intents.MuseumChoosingActivity

const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity() {
    private val url =
        "jdbc:mysql://b8fu1r5tflhnrnqjztht-mysql.services.clever-cloud.com:3306/b8fu1r5tflhnrnqjztht"
    private val username = "unxmdvotjktefgp8"
    private val password = "4XxtC2Ky5Dzz2AEEoC60"

    private lateinit var viewBinding: ActivityFindingMuseumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFindingMuseumBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        Thread {
            sqlConnection = SQLConnection.getSqlConnection()
            sqlConnection.connectServer(url, username, password)
            SystemClock.sleep(1000)
            if (!sqlConnection.isReconnecting) {
                val intent = Intent(this, ViewThing::class.java).apply {
                    putExtra(EXTRA_MESSAGE, "hello")
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
            }
        }.start()
    }

    companion object {
        lateinit var sqlConnection: SQLConnection

        fun showNotification(context: Context, content: String) {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
        }
    }
}
