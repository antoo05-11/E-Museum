package com.example.e_museum.fragments.fragments_view_thing

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.e_museum.PagerMarginItemDecoration
import com.example.e_museum.PaletteUtils
import com.example.e_museum.R
import com.example.e_museum.adapters.ThingImageListAdapter
import com.example.e_museum.databinding.FragmentViewThingImagesBinding
import com.example.e_museum.entities.Thing
import kotlin.math.abs

class ViewThingImagesFragment : Fragment() {
    private lateinit var binding: FragmentViewThingImagesBinding

    private lateinit var viewPager: ViewPager2
    private lateinit var myAdapter: ThingImageListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewThingImagesBinding.inflate(inflater, container, false)

        viewPager = binding.viewPager

        val thing = requireActivity().intent.getSerializableExtra("thing") as Thing
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
                    ) as ThingImageListAdapter.ThingImageViewHolder).imageView
                if (imageView.drawable != null) {
                    val imageBitmap = imageView.drawable.toBitmap()
                    val backgroundDominantColor = PaletteUtils().getDominantGradient(
                        imageBitmap,
                        0f,
                        GradientDrawable.Orientation.TOP_BOTTOM, null
                    )
                    binding.frameLayout.background = backgroundDominantColor
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
        val itemDecoration = PagerMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_horizontal_margin
        )
        viewPager.addItemDecoration(itemDecoration)
    }

}