package com.example.e_museum.view_controller.fragments.fragments_view_thing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.e_museum.view_controller.activities.MainActivity
import com.example.e_museum.databinding.ActivityViewQrBinding
import com.example.e_museum.entities.Thing
import com.squareup.picasso.Picasso

class ViewQRFragment : Fragment() {
    private lateinit var binding: ActivityViewQrBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        binding = ActivityViewQrBinding.inflate(inflater, container, false)

        val thing = activity?.intent?.extras?.getSerializable("thing") as Thing
        Picasso.get()
            .load(
                String.format(
                    MainActivity.fileServerURL + "qr_code_images/%d.png",
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