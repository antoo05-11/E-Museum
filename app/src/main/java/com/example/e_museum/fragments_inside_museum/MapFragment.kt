package com.example.e_museum.fragments_inside_museum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.e_museum.R
import com.example.e_museum.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null

    private val binding get() = _binding!!

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

        val photoView = binding.photoView;
        photoView.setImageResource(R.drawable.hcm_museum_map);
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}