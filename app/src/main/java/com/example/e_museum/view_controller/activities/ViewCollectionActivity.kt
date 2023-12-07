package com.example.e_museum.view_controller.activities

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.e_museum.R
import com.example.e_museum.view_controller.adapters.ThingsListAdapter
import com.example.e_museum.databinding.ActivityViewCollectionBinding
import com.example.e_museum.entities.Collection
import com.example.e_museum.entities.Thing
import com.example.e_museum.utils.MarginItemDecoration
import com.example.e_museum.utils.PaletteUtils
import kotlin.math.abs

class ViewCollectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewCollectionBinding
    private lateinit var adapter: ThingsListAdapter
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backInsideMuseumButton.setOnClickListener {
            finish()
        }

        viewPager = binding.thingImagesViewPager

        val collection = intent.getSerializableExtra("collection") as Collection
        binding.topBarTextView.text = collection.name

        val thingsList = collection.thingsList as ArrayList<Thing>

        adapter =
            ThingsListAdapter(
                this,
                thingsList
            )
        createCardHolder()

        viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val imageView =
                    ((viewPager.getChildAt(0) as RecyclerView).findViewHolderForAdapterPosition(
                        position
                    ) as ThingsListAdapter.ThingViewHolder).thingImageView
                if (imageView.drawable != null) {
                    val imageBitmap = imageView.drawable.toBitmap()
                    val backgroundDominantColor = PaletteUtils().getDominantGradient(
                        imageBitmap,
                        0f,
                        GradientDrawable.Orientation.TOP_BOTTOM, null
                    )
                    binding.root.background = backgroundDominantColor
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
        val itemDecoration = MarginItemDecoration(
            applicationContext,
            R.dimen.viewpager_horizontal_margin
        )
        viewPager.addItemDecoration(itemDecoration)
    }
}