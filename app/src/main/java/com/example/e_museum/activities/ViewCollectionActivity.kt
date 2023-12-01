package com.example.e_museum.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.e_museum.databinding.ActivityViewCollectionBinding

class ViewCollectionActivity : AppCompatActivity() {
    private lateinit var activityViewCollectionBinding: ActivityViewCollectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityViewCollectionBinding = ActivityViewCollectionBinding.inflate(layoutInflater)
    }
}