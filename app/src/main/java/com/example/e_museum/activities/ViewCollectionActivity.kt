package com.example.e_museum.activities

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.e_museum.R
import com.example.e_museum.adapters.ThingListAdapter
import com.example.e_museum.databinding.ActivityViewCollectionBinding
import com.example.e_museum.entities.Thing
import com.example.e_museum.utils.PagerMarginItemDecoration
import com.example.e_museum.utils.PaletteUtils
import kotlin.math.abs

class ViewCollectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewCollectionBinding
    private lateinit var adapter: ThingListAdapter
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewCollectionBinding.inflate(layoutInflater)

        viewPager = binding.thingImagesViewPager

        val thingList = ArrayList<Thing>()
        adapter = ThingListAdapter(this, thingList)

        createCardHolder()

        viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val imageView =
                    ((viewPager.getChildAt(0) as RecyclerView).findViewHolderForAdapterPosition(
                        position
                    ) as ThingListAdapter.ThingViewHolder).thingImageView
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
    }

    private fun createCardHolder() {
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = adapter
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