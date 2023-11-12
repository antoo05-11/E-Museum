package com.example.e_museum.thing_finding_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.e_museum.databinding.FragmentFindingThingByIdBinding

class FindingThingByIDFragment : Fragment() {

    private var _binding: FragmentFindingThingByIdBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindingThingByIdBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Thing ID input view config.
        binding.thingIdTv.text = ""
        val numButtons = arrayOf(
            binding.num0,
            binding.num1,
            binding.num2,
            binding.num3,
            binding.num4,
            binding.num5,
            binding.num6,
            binding.num7,
            binding.num8,
            binding.num9
        )
        for (i in 0..9) {
            numButtons[i].setOnClickListener {
                if (binding.thingIdTv.text.length > 5) return@setOnClickListener
                binding.thingIdTv.text =
                    String.format(binding.thingIdTv.text.toString() + numButtons[i].text.toString())
            }
        }
        binding.deleteNumButton.setOnClickListener {
            if (binding.thingIdTv.text.isNotEmpty()) {
                binding.thingIdTv.text =
                    binding.thingIdTv.text.substring(0, binding.thingIdTv.text.length - 1)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}