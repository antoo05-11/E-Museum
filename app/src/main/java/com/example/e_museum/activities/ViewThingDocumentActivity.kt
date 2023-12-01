package com.example.e_museum.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.e_museum.databinding.ActivityViewThingDocumentBinding
import com.example.e_museum.entities.Thing

class ViewThingDocumentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewThingDocumentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewThingDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thing = intent.extras?.getSerializable("thing") as Thing

        binding.backButton.setOnClickListener { finish() }
        binding.thingNameTextView.text = thing.name
        binding.thingAgeTextView.text = thing.age
        binding.thingDescriptionTextView.text = thing.description
        binding.thingOriginTextView.text = thing.origin
        binding.thingSizeTextView.text = thing.size
    }
}