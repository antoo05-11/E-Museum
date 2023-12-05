package com.example.e_museum.view_controller.fragments.fragments_inside_museum

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_museum.R
import com.example.e_museum.view_controller.activities.MainActivity
import com.example.e_museum.adapters.CollectionsListAdapter
import com.example.e_museum.data_fetching.models.Model
import com.example.e_museum.databinding.FragmentCollectionsListBinding
import com.example.e_museum.entities.Collection
import com.example.e_museum.entities.Museum
import com.example.e_museum.entities.Thing

class CollectionsListFragment : Fragment() {
    private lateinit var binding: FragmentCollectionsListBinding
    private lateinit var adapter: CollectionsListAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectionsListBinding.inflate(inflater, container, false)
        val museum = activity?.intent?.getSerializableExtra("museum") as Museum

        val collections = ArrayList<Collection>()
        adapter = CollectionsListAdapter(activity, collections)
        binding.collectionsListRcv.adapter = adapter
        binding.collectionsListRcv.layoutManager = LinearLayoutManager(requireActivity())

        collections.addAll(listOf(Collection(), Collection(), Collection()))

        val dividerItemDecoration = DividerItemDecoration(
            binding.collectionsListRcv.context,
            (binding.collectionsListRcv.layoutManager as LinearLayoutManager).orientation
        )
        ContextCompat.getDrawable(
            requireActivity().applicationContext,
            R.drawable.divider_shape
        )?.let {
            dividerItemDecoration.setDrawable(
                it
            )
        }
        binding.collectionsListRcv.addItemDecoration(dividerItemDecoration)

        Thread {
            val list = Model.getInstance().collectionModel.getCollectionByMuseumID(museum.museumID)
            collections.clear()
            collections.addAll(list)
            activity?.runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }.start()
        return binding.root
    }
}