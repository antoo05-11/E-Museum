package com.example.e_museum.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.e_museum.R
import com.example.e_museum.databinding.ActivityInsideMuseumBinding

class InsideMuseumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInsideMuseumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInsideMuseumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.background = null

        val navController = findNavController(R.id.nav_host_fragment_activity_inside_museum)
        binding.bottomNavigationView.setupWithNavController(navController)

        val search = binding.fab


        search.setOnClickListener {
            // Navigate to the desired fragment
            findNavController(R.id.nav_host_fragment_activity_inside_museum).navigate(R.id.fragment_finding_thing)

        }

        supportActionBar?.title = "Museum A"
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