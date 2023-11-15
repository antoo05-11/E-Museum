package com.example.e_museum

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ViewThing : AppCompatActivity() {
    lateinit var v_flipper: ViewFlipper
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thing_view)
        v_flipper = findViewById(R.id.view_flipper)

        val images = arrayOf(R.drawable.slide1, R.drawable.slide2, R.drawable.slide3)

        for (imageResource in images) {
            fliperImage(imageResource)
        }
        var sheet = findViewById<FrameLayout>(R.id.bottom_sheet)
        BottomSheetBehavior.from(sheet).apply{
            peekHeight = 250
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }

    fun fliperImage(imageResource: Int) {
        val imageView = ImageView(this)
        imageView.setImageResource(imageResource)

        // Adjust the layout parameters to fit the frame
        imageView.scaleType = ImageView.ScaleType.FIT_XY

        v_flipper.addView(imageView)
        v_flipper.flipInterval = 4000
        v_flipper.isAutoStart = true

        v_flipper.setInAnimation(this, android.R.anim.slide_in_left)
        v_flipper.setOutAnimation(this, android.R.anim.slide_out_right)
    }
}

