package com.example.e_museum.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e_museum.databinding.ActivityViewQrBinding
import com.example.e_museum.models.Thing
import com.squareup.picasso.Picasso

class ViewQRActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewQrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewQrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thing = intent.extras?.getSerializable("thing") as Thing
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

        binding.backButton.setOnClickListener { finish() }
    }
}