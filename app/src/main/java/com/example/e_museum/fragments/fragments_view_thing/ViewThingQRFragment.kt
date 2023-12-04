package com.example.e_museum.fragments.fragments_view_thing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.e_museum.activities.MainActivity
import com.example.e_museum.databinding.DeprecatedFragmentViewThingQrBinding
import com.example.e_museum.models.Thing
import com.squareup.picasso.Picasso

@Deprecated("Unused")
class ViewThingQRFragment : Fragment() {
    private lateinit var binding: DeprecatedFragmentViewThingQrBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DeprecatedFragmentViewThingQrBinding.inflate(inflater, container, false)
        val thing = requireActivity().intent.extras?.getSerializable("thing") as Thing
        Picasso.get()
            .load(
                String.format(
                    MainActivity.fileServerURL+"qr_code_images/%d.png",
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