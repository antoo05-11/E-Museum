package com.example.e_museum.view_controller.fragments.fragments_inside_museum

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.e_museum.R
import com.example.e_museum.view_controller.activities.MainActivity
import com.example.e_museum.view_controller.adapters.MapGuidesListAdapter
import com.example.e_museum.databinding.FragmentMapBinding
import com.example.e_museum.entities.MapGuide
import com.example.e_museum.entities.Museum
import com.example.e_museum.utils.MarginItemDecoration
import com.example.e_museum.view_controller.fragments.fragments_dialog.DataDenyConfirmDialogFragment
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlin.math.abs

private const val DEFAULT_FLOOR_INDEX = 1

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: MapGuidesListAdapter

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

        val dropdown: Spinner = binding.spinner1
        val items = listOf("Tầng 1", "Tầng 2", "Tầng 3")
        val floorsListAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        dropdown.adapter = floorsListAdapter

        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    activity?.runOnUiThread {
                        if (!activity!!.supportFragmentManager.isStateSaved) {
                            val dialogFragment: DialogFragment =
                                DataDenyConfirmDialogFragment(activity!!)
                            dialogFragment.show(activity!!.supportFragmentManager, "confirm")
                        }
                    }
                    parent?.setSelection(0)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        Picasso.get().load(
            String.format(
                MainActivity.fileServerURL + "map_images/%d_%d.png",
                museum.museumID, DEFAULT_FLOOR_INDEX
            )
        ).into(photoView, object : Callback {
            override fun onSuccess() {
                bitmap = photoView.drawable.toBitmap()
                photoView.setImageBitmap(bitmap)
                scaleRate = bitmap.width.toFloat().div(photoView.drawable.intrinsicWidth)

                photoView.attacher.scaleType = ImageView.ScaleType.CENTER_CROP
                photoView.mediumScale = 1.2f
                photoView.maximumScale = 1.5f
                viewPager.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)

                        val mapGuide = mapGuides[position]
                        val x = mapGuide.posX.toFloat() * scaleRate
                        val y = mapGuide.posY.toFloat() * scaleRate
                        val focalX: Float = (x * photoView.right) / bitmap.width
                        val focalY: Float = (y * photoView.bottom) / bitmap.height

                        photoView.setScale(
                            1.5f,
                            focalX,
                            focalY,
                            false
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
                    adapter =
                        MapGuidesListAdapter(
                            activity,
                            mapGuides
                        )
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
        val itemDecoration = MarginItemDecoration(
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