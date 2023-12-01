package com.example.e_museum.fragments.fragments_inside_museum

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_museum.R
import com.example.e_museum.databinding.FragmentThingsListBinding

class ThingsListFragment : Fragment() {
    private lateinit var binding: FragmentThingsListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThingsListBinding.inflate(inflater, container, false)
        return binding.root
    }
}