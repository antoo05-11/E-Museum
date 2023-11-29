package com.example.e_museum.fragments.fragments_view_thing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.e_museum.databinding.FragmentViewThingQrBinding
import com.example.e_museum.entities.Thing
import com.squareup.picasso.Picasso

class ViewThingQRFragment : Fragment() {
    private lateinit var binding: FragmentViewThingQrBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewThingQrBinding.inflate(inflater, container, false)
        val thing = requireActivity().intent.extras?.getSerializable("thing") as Thing
        Picasso.get()
            .load(
                String.format(
                    "https://muzik-files-server.000webhostapp.com/emuseum/qr_code_images/%d.png",
                    thing.thingID
                )
            )
            .fit()
            .centerInside()
            .into(binding.qrImage)
        binding.idTextView.text = thing.thingID.toString()
        binding.thingNameTv.text = thing.name
        return binding.root
    }
}