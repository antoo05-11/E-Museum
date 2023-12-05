package com.example.e_museum.view_controller.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.ViewModelProvider
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.e_museum.R
import com.example.e_museum.adapters.ThingImagesListAdapter
import com.example.e_museum.databinding.ActivityViewThingBinding
import com.example.e_museum.entities.Thing
import com.example.e_museum.utils.MarginItemDecoration
import com.example.e_museum.utils.PaletteUtils
import com.example.e_museum.utils.getReadableTime
import com.example.e_museum.utils.printLogcat
import kotlin.math.abs

class ViewThingActivity : AppCompatActivity() {

    lateinit var binding: ActivityViewThingBinding
        private set
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var player: ExoPlayer
    lateinit var thing: Thing
        private set

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerViewModel = ViewModelProvider(this)[PlayerViewModel::class.java]
        player = ExoPlayer.Builder(applicationContext).build()
        playerViewModel.exoPlayerMutableLiveData.value = player
        playerViewModel.addListener()

        binding = ActivityViewThingBinding.inflate(layoutInflater)

        thing = intent.getSerializableExtra("thing") as Thing

        val navFragment =
            supportFragmentManager.findFragmentById(R.id.thing_view_fragment_nav_host) as NavHostFragment
        val navController = navFragment.navController

        binding.backViewThingButton.setOnClickListener {
            if (navController.currentDestination?.id != R.id.fragmentViewThing) {
                navController.popBackStack()
                navFragment.view?.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    bottomToTop = binding.bottomNavigationView.id
                }
                binding.bottomNavigationView.isVisible = true
            } else {
                finish()
            }
        }
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            binding.bottomNavigationView.isVisible = false
            navFragment.view?.updateLayoutParams<ConstraintLayout.LayoutParams> {
                bottomToBottom = binding.thingViewRoot.id
            }
            navController.navigate(menuItem.itemId)
            true
        }

        val thingURLLists = ArrayList<String>()
        repeat(thing.images) { i ->
            thingURLLists.add(
                "${MainActivity.fileServerURL}thing_images/${thing.thingID}_${i + 1}.png"
            )
        }

        binding.shareButton.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND_MULTIPLE

                val imageUris = ArrayList<Uri>()
                for (i in 0 until thingURLLists.size) {
                    imageUris.add(Uri.parse(thingURLLists[i]))
                }

                putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
                putExtra(Intent.EXTRA_TEXT, thing.name + "\n" + getString(R.string.share_content))

                type = "image/*"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}

