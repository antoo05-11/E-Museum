package com.example.e_museum.view_controller.activities

import android.net.Uri
import android.os.Handler
import android.widget.SeekBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.e_museum.entities.Thing

class PlayerViewModel : ViewModel() {

    var exoPlayerMutableLiveData: MutableLiveData<ExoPlayer> = MutableLiveData()
        private set

    var playingMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
        private set

    var thingMutableLiveData: MutableLiveData<Thing> = MutableLiveData()
        private set

    var currentTimeMutableLiveData: MutableLiveData<Int> = MutableLiveData()
        private set

    var isSelectedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
        private set

    init {
        updateCurrentProgress()
        isSelectedMutableLiveData.value = false
    }

    class OnSeekBarChangeListener(private val playerViewModel: PlayerViewModel) :
        SeekBar.OnSeekBarChangeListener {
        private var progressValue = 0
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            progressValue = seekBar!!.progress
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            seekBar!!.progress = progressValue
            playerViewModel.currentTimeMutableLiveData.value = progressValue
            playerViewModel.player.seekTo(seekBar.progress.toLong())
        }
    }

    val player get() = exoPlayerMutableLiveData.value!!

    fun setPlaying(isPlaying: Boolean) {
        playingMutableLiveData.value = isPlaying
    }

    fun setThing(thing: Thing) {
        isSelectedMutableLiveData.value = true
        thingMutableLiveData.value = thing
    }

    fun addListener() {
        player.addListener(
            object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    setPlaying(isPlaying)
                    updateCurrentProgress()
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    updateCurrentProgress()
                }
            }
        )
    }

    fun seekNext10s() {
        val currentPosition: Long = player.currentPosition
        if (currentPosition + 10000 < thingMutableLiveData.value!!.duration) {
            player.seekTo(currentPosition + 10000)
        } else player.seekTo(thingMutableLiveData.value!!.duration.toLong())
    }

    fun seekPrevious10s() {
        val currentPosition: Long = player.currentPosition
        if (currentPosition - 10000 > 0) {
            player.seekTo(currentPosition - 10000)
        } else player.seekTo(0)
    }

    fun skipNextThing() {
        if (player.hasNextMediaItem()) {
            player.seekToNext()
        }
    }

    fun skipPreThing() {
        if (player.hasPreviousMediaItem()) {
            player.seekToPrevious()
        }
    }

    fun playPause() {
        if (player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    fun pause() {
        player.pause()
    }

    fun setMedia(uri: Uri) {
        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    fun stop() {
        player.stop()
    }

    private fun updateCurrentProgress() {
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                if (player.isPlaying) {
                    currentTimeMutableLiveData.value = player.currentPosition.toInt()
                }
                handler.postDelayed(this, 1000)
            }
        })
    }
}