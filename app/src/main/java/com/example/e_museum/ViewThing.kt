package com.example.e_museum

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.CodeScanner
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ViewThing : AppCompatActivity() {
    lateinit var v_flipper: ViewFlipper
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thing_view)

    }

}

