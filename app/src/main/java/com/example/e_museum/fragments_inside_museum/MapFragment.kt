package com.example.e_museum.fragments_inside_museum

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.e_museum.R
import com.example.e_museum.databinding.FragmentMapBinding


class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null

    private val binding get() = _binding!!

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

        val photoView = binding.photoView;
        photoView.setImageResource(R.drawable.demomap);

        val demoBut = binding.button2
        demoBut.setOnClickListener { v ->
            // Define the center point for zoom
            val centerX = 150f
            val centerY = 150f

            // Define the desired scale factor
            val scaleFactor = 2.0f // Change this value according to your requirement

            // Zoom in with a focal point at (centerX, centerY)
            photoView.setScale(scaleFactor, centerX, centerY, true)
        }


        val resetBut = binding.button3;
        resetBut.setOnClickListener { v ->
            // Đặt lại các thuộc tính về giá trị mặc định
            photoView.scale = 1f
            photoView.setScale(1f, 0f, 0f, true)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}