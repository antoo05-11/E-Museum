package com.example.e_museum.fragments.fragments_inside_museum

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.e_museum.R
import com.example.e_museum.activities.MainActivity
import com.example.e_museum.adapters.MapGuideListAdapter
import com.example.e_museum.databinding.FragmentMapBinding
import com.example.e_museum.entities.MapGuide
import com.example.e_museum.entities.Museum
import com.example.e_museum.utils.PagerMarginItemDecoration
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import kotlin.math.abs

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: MapGuideListAdapter

    private lateinit var bitmap: Bitmap

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val photoView: PhotoView = binding.photoView
        var scaleRate: Float

        viewPager = binding.viewPager

        val mapGuides: MutableList<MapGuide> = mutableListOf()

        val museum = activity?.intent?.getSerializableExtra("museum") as Museum
        Picasso.get().load(
            String.format(
                "https://muzik-files-server.000webhostapp.com/emuseum/map_images/%d_%d.png",
                museum.museumID, 1
            )
        ).into(photoView, object : Callback {
            override fun onSuccess() {

                bitmap = photoView.drawable.toBitmap()
                photoView.setImageBitmap(bitmap)
                scaleRate = bitmap.width.toFloat().div(photoView.drawable.intrinsicWidth)

                photoView.attacher.scaleType = ImageView.ScaleType.CENTER_CROP

                viewPager.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)

                        photoView.attacher.setScale(
                            1f,
                            true
                        )

                        val mapGuide = mapGuides[position]
                        val x = mapGuide.posX.toFloat() * scaleRate
                        val y = mapGuide.posY.toFloat() * scaleRate

                        val focalX: Float = (x * photoView.right) / bitmap.width
                        val focalY: Float = (y * photoView.bottom) / bitmap.height

                        photoView.attacher.setScale(
                            2f,
                            focalX,
                            focalY,
                            true
                        )
                    }
                })
            }

            override fun onError(e: Exception?) {
            }
        })

        Thread {
            val queryString = String.format(
                "select * from maps where museumID = %d", museum.museumID
            )
            val resultSet = MainActivity.sqlConnection.getDataQuery(queryString)
            if (resultSet == null || !resultSet.isBeforeFirst) {
                return@Thread
            } else {
                while (resultSet.next()) {
                    mapGuides.add(MapGuide(resultSet))
                }
                activity?.runOnUiThread {
                    adapter = MapGuideListAdapter(activity, mapGuides)
                    createCardHolder()
                }
            }
        }.start()

        return root
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
            requireContext(),
            R.dimen.viewpager_horizontal_margin
        )
        viewPager.addItemDecoration(itemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}