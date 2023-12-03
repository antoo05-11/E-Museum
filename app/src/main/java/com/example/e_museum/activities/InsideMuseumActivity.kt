package com.example.e_museum.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
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

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_notifications -> {
                    binding.topBarTextView.text = getString(R.string.event_notification)
                }

                R.id.navigation_collection -> {
                    binding.topBarTextView.text = getString(R.string.list_collection)
                }

                else -> {
                    binding.topBarTextView.text = ""
                }
            }
            navController.navigate(menuItem.itemId)
            true
        }

        val search = binding.fab
        search.setOnClickListener {
            if (navController.currentDestination?.id == R.id.fragment_finding_thing) {
                return@setOnClickListener
            }
            navController.popBackStack()
            binding.topBarTextView.text = ""
            val menu = binding.bottomNavigationView.menu
            menu.setGroupCheckable(0, true, false)
            for (i in 0 until menu.size()) {
                menu.getItem(i).isChecked = false
            }
            menu.setGroupCheckable(0, true, true)

            navController.navigate(R.id.fragment_finding_thing)
        }

        Glide.with(this).load(R.drawable.icons8_search)
            .into(binding.fab)

        binding.backInsideMuseumButton.setOnClickListener {
            if (binding.bottomNavigationView.selectedItemId == R.id.navigation_home
                && navController.currentDestination?.id != R.id.fragment_finding_thing
            ) {
                finish()
            } else {
                binding.bottomNavigationView.selectedItemId = R.id.navigation_home
            }
        }
    }
}