package com.example.e_museum.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.e_museum.R
import com.example.e_museum.databinding.ActivityViewThingBinding
import com.example.e_museum.entities.Thing

class ViewThingActivity : AppCompatActivity() {

    private lateinit var exoPlayer: ExoPlayer

    private lateinit var binding: ActivityViewThingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewThingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navController = findNavController(R.id.view_thing_fragment)
        NavigationUI.setupWithNavController(binding.viewThingBottomNavView, navController)

        val thing = intent.getSerializableExtra("thing") as Thing

        supportActionBar?.title = thing.name

        exoPlayer = ExoPlayer.Builder(this.applicationContext).build()
        binding.videoView.player = exoPlayer
        exoPlayer.setMediaItem(
            MediaItem.fromUri(
                String.format(
                    "https://muzik-files-server.000webhostapp.com/emuseum/museum_sound/%d.mp3",
                    thing.thingID
                )
            )
        )

        exoPlayer.prepare()
        exoPlayer.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

