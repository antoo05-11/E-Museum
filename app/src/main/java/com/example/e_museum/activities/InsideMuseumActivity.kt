package com.example.e_museum.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.e_museum.R
import com.example.e_museum.databinding.ActivityInsideMuseumBinding
import com.example.e_museum.entities.Museum

class InsideMuseumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInsideMuseumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInsideMuseumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val museum = intent.getSerializableExtra("museum") as Museum

        binding.bottomNavigationView.background = null

        val navController = findNavController(R.id.nav_host_fragment_activity_inside_museum)
        binding.bottomNavigationView.setupWithNavController(navController)

        val search = binding.fab

        search.setOnClickListener {
            navController.popBackStack()

            val menu = binding.bottomNavigationView.menu
            menu.setGroupCheckable(0, true, false)
            for (i in 0 until menu.size()) {
                menu.getItem(i).isChecked = false
            }
            menu.setGroupCheckable(0, true, true)

            navController.navigate(R.id.fragment_finding_thing)
        }

        Glide.with(this).load(R.drawable.qr_scanner)
            .into(binding.fab)

        supportActionBar?.title = museum.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}