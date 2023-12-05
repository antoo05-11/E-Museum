package com.example.e_museum.view_controller.fragments.fragments_view_thing

import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.e_museum.R
import com.example.e_museum.view_controller.activities.MainActivity
import com.example.e_museum.view_controller.activities.PlayerViewModel
import com.example.e_museum.view_controller.activities.ViewThingActivity
import com.example.e_museum.adapters.ThingImagesListAdapter
import com.example.e_museum.databinding.FragmentViewThingBinding
import com.example.e_museum.entities.Thing
import com.example.e_museum.utils.MarginItemDecoration
import com.example.e_museum.utils.PaletteUtils
import com.example.e_museum.utils.getReadableTime
import kotlin.math.abs

class ViewThingFragment : Fragment() {

    private lateinit var binding: FragmentViewThingBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var myAdapter: ThingImagesListAdapter
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var thing: Thing

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerViewModel = ViewModelProvider(requireActivity())[PlayerViewModel::class.java]
        binding.seekBar.setOnSeekBarChangeListener(
            PlayerViewModel.OnSeekBarChangeListener(
                playerViewModel
            )
        )
        binding.playButton.setOnClickListener {
            playerViewModel.playPause()
        }
        playerViewModel.playingMutableLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.playButton.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.icons8_pause_48)
            } else {
                binding.playButton.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.icons8_play_48)
            }
        }

        playerViewModel.thingMutableLiveData.observe(viewLifecycleOwner) {
            binding.tvTotal.text = getReadableTime(it.duration)
            binding.seekBar.max = it.duration
        }
        playerViewModel.currentTimeMutableLiveData.observe(viewLifecycleOwner) {
            binding.seekBar.progress = it
            binding.tvCurrent.text = getReadableTime(it)
        }

        if (playerViewModel.playingMutableLiveData.value != true) {
            playerViewModel.stop()
            playerViewModel.setMedia(
                Uri.parse(MainActivity.fileServerURL + "museum_sound/${thing.thingID}.mp3")
            )
            playerViewModel.setThing(thing)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewThingBinding.inflate(inflater, container, false)
        binding.thingNameMainTextView.isSelected = true

        thing = activity?.intent?.getSerializableExtra("thing") as Thing
        val thingURLLists = ArrayList<String>()

        repeat(thing.images) { i ->
            thingURLLists.add(
                "${MainActivity.fileServerURL}thing_images/${thing.thingID}_${i + 1}.png"
            )
        }

        binding.thingNameMainTextView.text = thing.name

        viewPager = binding.viewPager
        myAdapter = ThingImagesListAdapter(
            requireActivity(),
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
                    ) as ThingImagesListAdapter.ThingImageViewHolder).imageView
                if (imageView.drawable != null) {
                    val imageBitmap = imageView.drawable.toBitmap()
                    val backgroundDominantColor = PaletteUtils().getDominantGradient(
                        imageBitmap,
                        0f,
                        GradientDrawable.Orientation.TOP_BOTTOM, null
                    )
                    (requireActivity() as ViewThingActivity).binding.thingViewRoot.background =
                        backgroundDominantColor
                }
            }
        })

        return binding.root
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
        val itemDecoration = MarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_horizontal_margin
        )
        viewPager.addItemDecoration(itemDecoration)
    }
}