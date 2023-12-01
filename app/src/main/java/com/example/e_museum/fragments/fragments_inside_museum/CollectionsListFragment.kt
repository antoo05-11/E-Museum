package com.example.e_museum.fragments.fragments_inside_museum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.e_museum.activities.MainActivity
import com.example.e_museum.adapters.CollectionsListAdapter
import com.example.e_museum.databinding.FragmentCollectionsListBinding
import com.example.e_museum.entities.Collection
import com.example.e_museum.entities.Museum
import com.example.e_museum.entities.Thing

class CollectionsListFragment : Fragment() {
    private lateinit var binding: FragmentCollectionsListBinding
    private lateinit var adapter: CollectionsListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectionsListBinding.inflate(inflater, container, false)
        val museum = activity?.intent?.getSerializableExtra("museum") as Museum

        // TODO: ...
        val collections = mutableListOf(Collection(), Collection(), Collection())
        adapter = CollectionsListAdapter()

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
                collections.add(Collection(collectionResultSet, thingsList))
            }
        }.start()
        return binding.root
    }
}