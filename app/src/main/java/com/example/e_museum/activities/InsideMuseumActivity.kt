package com.example.e_museum.activities

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.e_museum.R
import com.example.e_museum.databinding.ActivityInsideMuseumBinding
import com.example.e_museum.entities.Museum

class InsideMuseumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInsideMuseumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInsideMuseumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_inside_museum)
        binding.navView.setupWithNavController(navController)

        supportActionBar?.title = (intent.getSerializableExtra("museum") as Museum).name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
           onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}