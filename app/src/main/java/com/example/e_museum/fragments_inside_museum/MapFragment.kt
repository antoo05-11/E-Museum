package com.example.e_museum.fragments_inside_museum

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.e_museum.MainActivity
import com.example.e_museum.PagerMarginItemDecoration
import com.example.e_museum.R
import com.example.e_museum.activities.ViewThingActivity
import com.example.e_museum.adapters.MapGuideListAdapter
import com.example.e_museum.databinding.FragmentMapBinding
import com.example.e_museum.entities.Thing
import com.google.common.collect.Lists
import kotlin.math.abs


class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2;

    private lateinit var adapter: MapGuideListAdapter;

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Museum A"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewPager = binding.viewPager

        val titles: MutableList<String> = mutableListOf()
        val contents: MutableList<String> = mutableListOf()
        val urls = listOf("abc", "abc", "abc")
       // val url: MutableList<String> = mutableListOf()

        Thread{
            val queryString = String.format(
                "select * from maps where mapID = 1"
            )
            val resultSet = MainActivity.sqlConnection.getDataQuery(queryString)
            if (resultSet == null) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Wrong map", Toast.LENGTH_SHORT).show()
                }
                return@Thread
            }else{
                while (resultSet.next()) {
                    val title = resultSet.getString("title")
                    val content = resultSet.getString("content")

                    titles.add(title)
                    contents.add(content)

                }
                requireActivity().runOnUiThread{
                    adapter = MapGuideListAdapter(requireActivity(), titles, contents, urls)
                    createCardHolder()
                }
            }

        }.start()

//         val bitmap = photoView.drawable.toBitmap()
//
//        val options = BitmapFactory.Options()
//        options.inTargetDensity = DisplayMetrics.DENSITY_DEFAULT
//        val bd = BitmapFactory.decodeResource(
//            requireActivity().resources,
//            R.drawable.demomap,
//            options
//        )
//        val imageRealWidth = bd.width.toFloat()

       // val scaleRate = bitmap.width / imageRealWidth;

//        val demoBut = binding.button2
//
//        Log.d("bitmap-size", "${bitmap.width.toFloat()}x${bitmap.height.toFloat()}") //1675.0x1607.0
//
//        demoBut.setOnClickListener {
//            Log.d("scaleRate", scaleRate.toString()) //2.625392
//
//            val x = 442f * scaleRate
//            val y = 300f * scaleRate
//
//            Log.d("focal x - y", "${x}-${y}") //1160.4232-787.6176
//
//            val focalX: Float = x * photoView.right / bitmap.width
//            val focalY: Float = y * photoView.bottom / bitmap.height
//
//            val targetScale = 3f
//            photoView.attacher.setScale(targetScale, focalX, focalY, false)
//        }

        val photoView = binding.photoView;
        photoView.setImageResource(R.drawable.demomap);

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