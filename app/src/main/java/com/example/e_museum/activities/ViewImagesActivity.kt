package com.example.e_museum.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import com.example.e_museum.R
import com.example.e_museum.databinding.ActivityViewImagesBinding
import com.squareup.picasso.Picasso

class ViewImagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewImagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.background = null
        binding.backButton.setOnClickListener{
            finish()
        }
        Picasso.get().load(intent.extras?.getString("imageURL")).into(binding.photoView)
    }
}