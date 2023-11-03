package com.example.e_museum

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.e_museum.adapters.MuseumListAdapter
import com.example.e_museum.databinding.MuseumChoosingViewBinding
import com.example.e_museum.entities.Museum


class MuseumChoosingActivity : ComponentActivity() {
    private lateinit var viewBinding: MuseumChoosingViewBinding

    private fun showNotification(content: String) {
        Toast.makeText(applicationContext, content, Toast.LENGTH_SHORT).show();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = MuseumChoosingViewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.backButton.setOnClickListener {
            finish()
        }
        val museums = ArrayList<Museum>()
        Thread {
            val resultSet = MainActivity.sqlConnection.getDataQuery("select * from museums");
            while (resultSet.next()) {
                museums.add(Museum(resultSet))
            }
            runOnUiThread {
                viewBinding.museumList.adapter =
                    MuseumListAdapter(
                        this,
                        museums
                    )
            }
        }.start()


    }
}



