package com.example.e_museum.view_controller.activities

import android.os.Bundle
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.example.e_museum.databinding.ActivityViewVideoBinding
import com.example.e_museum.entities.Thing

class ViewVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewVideoBinding
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var thing: Thing
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backViewThingButton.setOnClickListener {
            exoPlayer.stop()
            exoPlayer.release()
            finish()
        }
        thing = intent.getSerializableExtra("thing") as Thing
        preparePlayer()
    }


    @OptIn(UnstableApi::class)
    private fun preparePlayer() {
        exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer.playWhenReady = true
        binding.videoView.player = exoPlayer
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaItem =
            MediaItem.fromUri(
                String.format(
                    "${MainActivity.fileServerURL}thing_videos/%d_%d.mp4",
                    thing.museumID, thing.thingID
                )
            )
        val mediaSource = ProgressiveMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(mediaItem)
        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.playWhenReady = true
        exoPlayer.prepare()
    }

}