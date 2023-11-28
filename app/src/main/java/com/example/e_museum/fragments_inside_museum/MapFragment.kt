package com.example.e_museum.fragments_inside_museum

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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