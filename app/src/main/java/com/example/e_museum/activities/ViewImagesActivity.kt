package com.example.e_museum.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import com.example.e_museum.R
import com.example.e_museum.databinding.ActivityViewImagesBinding

class ViewImagesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewImagesBinding
    private lateinit var vFlipper: ViewFlipper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vFlipper = binding.viewFlipper

        val images = arrayOf(R.drawable.slide1, R.drawable.slide2, R.drawable.slide3)

        for (imageResource in images) {
            flipperImage(imageResource)
        }

    }

    private fun flipperImage(imageResource: Int) {
        val imageView = ImageView(this)
        imageView.setImageResource(imageResource)

        imageView.scaleType = ImageView.ScaleType.FIT_XY

        vFlipper.addView(imageView)

        vFlipper.setInAnimation(this, android.R.anim.slide_in_left)
        vFlipper.setOutAnimation(this, android.R.anim.slide_out_right)
    }
}