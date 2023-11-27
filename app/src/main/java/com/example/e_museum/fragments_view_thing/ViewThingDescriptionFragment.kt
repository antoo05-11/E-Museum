package com.example.e_museum.fragments_view_thing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.e_museum.databinding.FragmentViewThingDescriptionBinding
import com.example.e_museum.entities.Thing

class ViewThingDescriptionFragment : Fragment() {
    private lateinit var binding: FragmentViewThingDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewThingDescriptionBinding.inflate(inflater, container, false)
        val thing = requireActivity().intent.getSerializableExtra("thing") as Thing
        binding.thingAgeTextView.text = thing.age
        binding.thingDescriptionTextView.text = thing.description
        binding.thingOriginTextView.text = thing.origin
        binding.thingSizeTextView.text = thing.size
        return binding.root
    }
}