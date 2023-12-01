package com.example.e_museum.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.MutableLiveData
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.e_museum.R
import com.example.e_museum.adapters.ThingImageListAdapter
import com.example.e_museum.databinding.ActivityViewThingBinding
import com.example.e_museum.entities.Thing
import com.example.e_museum.fragments.fragments_view_thing.ViewThingQRFragment
import com.example.e_museum.utils.PagerMarginItemDecoration
import com.example.e_museum.utils.PaletteUtils
import kotlin.math.abs

class ViewThingActivity : AppCompatActivity() {

    private lateinit var exoPlayer: ExoPlayer

    private lateinit var binding: ActivityViewThingBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var myAdapter: ThingImageListAdapter

    private var isPlaying: MutableLiveData<Boolean> = MutableLiveData(false)

    var currentTimeMutableLiveData: MutableLiveData<Int> = MutableLiveData()
        private set

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewThingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backViewThingButton.setOnClickListener {
            finish()
        }

        val thing = intent.getSerializableExtra("thing") as Thing
        binding.thingNameMainTextView.text = thing.name

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.qrMenuItem -> {
                    val intent = Intent(this, ViewQRActivity::class.java).apply {
                        putExtra("thing", thing)
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }

                R.id.documentMenuItem -> {
                    val intent = Intent(this, ViewThingDocumentActivity::class.java).apply {
                        putExtra("thing", thing)
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
            true
        }

        exoPlayer = ExoPlayer.Builder(this.applicationContext).build()
        exoPlayer.setMediaItem(
            MediaItem.fromUri(
                String.format(
                    "https://muzik-files-server.000webhostapp.com/emuseum/museum_sound/%d.mp3",
                    thing.thingID
                )
            )
        )
        exoPlayer.prepare()

        binding.playButton.setOnClickListener {
            isPlaying.value = !isPlaying.value!!
        }

        isPlaying.observe(this) {
            if (it) {
                binding.playButton.background =
                    ContextCompat.getDrawable(this, R.drawable.icons8_pause_48)
                exoPlayer.play()
            } else {
                binding.playButton.background =
                    ContextCompat.getDrawable(this, R.drawable.icons8_play_48)
                exoPlayer.pause()
            }
        }

        viewPager = binding.viewPager

        val thingURLLists = ArrayList<String>()
        for (i in 1..thing.images) {
            thingURLLists.add(
                String.format(
                    "https://muzik-files-server.000webhostapp.com/emuseum/thing_images/%d_%d.png",
                    thing.thingID,
                    i
                )
            )
        }
        myAdapter = ThingImageListAdapter(
            this,
            thingURLLists
        )

        createCardHolder()
        viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val imageView =
                    ((viewPager.getChildAt(0) as RecyclerView).findViewHolderForAdapterPosition(
                        position
                    ) as ThingImageListAdapter.ThingImageViewHolder).imageView
                if (imageView.drawable != null) {
                    val imageBitmap = imageView.drawable.toBitmap()
                    val backgroundDominantColor = PaletteUtils().getDominantGradient(
                        imageBitmap,
                        0f,
                        GradientDrawable.Orientation.TOP_BOTTOM, null
                    )
                    binding.thingViewRoot.background = backgroundDominantColor
                }
            }
        })


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

    private fun createCardHolder() {
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = myAdapter
        viewPager.offscreenPageLimit = 1

        val nextItemVisibleWidth = resources.getDimension(R.dimen.next_item_visible_width)
        val currentItemMargin =
            resources.getDimension(R.dimen.viewpager_horizontal_margin)
        val pageTranslation = nextItemVisibleWidth + currentItemMargin
        viewPager.setPageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslation * position
            page.scaleY = 1 - (0.25f * abs(position))
            page.alpha = 0.25f + (1 - abs(position))
        }
        val itemDecoration = PagerMarginItemDecoration(
            applicationContext,
            R.dimen.viewpager_horizontal_margin
        )
        viewPager.addItemDecoration(itemDecoration)
    }
}

