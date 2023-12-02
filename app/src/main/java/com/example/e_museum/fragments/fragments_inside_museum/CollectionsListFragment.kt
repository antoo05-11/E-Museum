package com.example.e_museum.fragments.fragments_inside_museum

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
import com.example.e_museum.activities.MainActivity
import com.example.e_museum.adapters.CollectionsListAdapter
import com.example.e_museum.databinding.FragmentCollectionsListBinding
import com.example.e_museum.entities.Collection
import com.example.e_museum.entities.Museum
import com.example.e_museum.entities.Thing
import com.example.e_museum.utils.printLogcat

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

        // TODO: ...
        val collections = mutableListOf(Collection(), Collection(), Collection())
        adapter = CollectionsListAdapter(activity, collections)
        binding.collectionsListRcv.adapter = adapter
        binding.collectionsListRcv.layoutManager = LinearLayoutManager(requireActivity())

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
            val resultSet = MainActivity.sqlConnection.getDataQuery(
                String.format(
                    "select * from collections where museumID = %d",
                    museum.museumID
                )
            )
            collections.clear()
            while (resultSet.next()) {
                val thingsList = mutableListOf<Thing>()
                val collectionResultSet = MainActivity.sqlConnection.getDataQuery(
                    String.format(
                        "select * from things where collectionID = %d",
                        resultSet.getInt("collectionID")
                    )
                )
                while (collectionResultSet.next()) {
                    thingsList.add(Thing(collectionResultSet))
                }
                collections.add(Collection(resultSet, thingsList))
                activity?.runOnUiThread {
                    adapter.notifyDataSetChanged()
                }
            }
        }.start()
        return binding.root
    }
}