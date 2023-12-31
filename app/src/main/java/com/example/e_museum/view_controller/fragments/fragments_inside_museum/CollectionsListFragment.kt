package com.example.e_museum.view_controller.fragments.fragments_inside_museum

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_museum.R
import com.example.e_museum.view_controller.adapters.CollectionsListAdapter
import com.example.e_museum.data_fetching.models.Model
import com.example.e_museum.databinding.FragmentCollectionsListBinding
import com.example.e_museum.entities.Collection
import com.example.e_museum.entities.Museum

class CollectionsListFragment : Fragment() {
    private lateinit var adapter: CollectionsListAdapter
    private var _binding: FragmentCollectionsListBinding? = null

    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val museum = activity?.intent?.getSerializableExtra("museum") as Museum

        val collections = ArrayList<Collection>()
        adapter =
            CollectionsListAdapter(
                activity,
                collections
            )
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
            if (list != null)
                collections.addAll(list)
            activity?.runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }.start()

        binding.collectionSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectionsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}