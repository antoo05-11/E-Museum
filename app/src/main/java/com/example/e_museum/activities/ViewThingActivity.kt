package com.example.e_museum.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.widget.ViewPager2
import com.example.e_museum.MainActivity
import com.example.e_museum.PagerMarginItemDecoration
import com.example.e_museum.R
import com.example.e_museum.adapters.ThingImageListAdapter
import com.example.e_museum.databinding.ActivityViewThingBinding
import kotlin.math.abs

class ViewThingActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var myAdapter: ThingImageListAdapter
    private lateinit var exoPlayer: ExoPlayer

    private lateinit var binding: ActivityViewThingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewThingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val thingID = intent.getIntExtra("thingID", -1)
        val navController = findNavController(R.id.view_thing_fragment)
        NavigationUI.setupWithNavController(binding.viewThingBottomNavView, navController)

        Thread {
            val resultSet = MainActivity.sqlConnection.getDataQuery(
                String.format(
                    "select * from things where thingID = %d",
                    thingID
                )
            )
            if (resultSet.next()) {

            }
        }.start()

//        viewPager = binding.viewPager
//        myAdapter = ThingImageListAdapter(
//            this,
//            listOf("abc.com", "bcd.com", "bcd.com", "bcd.com", "bcd.com")
//        )
//        createCardHolder()

        exoPlayer = ExoPlayer.Builder(this.applicationContext).build()
        binding.videoView.player = exoPlayer
        exoPlayer.setMediaItem(
            MediaItem.fromUri(
                "https://muzik-server-uet-i4e7.onrender.com/api/song/2/c01c0607e90d03d70f65muzikUETK668f4e24bbc98ed8a8d2b63942bb864188muzikUETK66e1d2777ce45efbc9225e109bef1ecc68329acdc605db7e29ab81522efd145248.m3u8"
            )
        )
        exoPlayer.prepare()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

