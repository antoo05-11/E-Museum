package com.example.e_museum.activities

import android.graphics.Typeface
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.e_museum.databinding.ActivityViewThingDocumentBinding
import com.example.e_museum.models.Thing
import hakobastvatsatryan.DropdownTextView

class ViewThingDocumentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewThingDocumentBinding

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewThingDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thing = intent.extras?.getSerializable("thing") as Thing
        binding.backButton.setOnClickListener { finish() }

        formatDropdownTextView(binding.originDropdownTextView, thing.origin)
        formatDropdownTextView(binding.ageDropdownTextView, thing.age)
        formatDropdownTextView(binding.thingDescriptionDropdownTextView, thing.description)
        binding.thingDescriptionDropdownTextView.getContentTextView().justificationMode =
            JUSTIFICATION_MODE_INTER_WORD
        binding.originDropdownTextView.getContentTextView().justificationMode =
            JUSTIFICATION_MODE_INTER_WORD
        formatDropdownTextView(binding.thingSizeDropdownTextView, thing.size)

        binding.thingNameTextView.text = thing.name
    }

    private fun formatDropdownTextView(dropdownTextView: DropdownTextView, content: String) {
        dropdownTextView.getTitleTextView().typeface = Typeface.DEFAULT_BOLD
        dropdownTextView.getContentTextView().textSize = 18f
        dropdownTextView.getTitleTextView().textSize = 18f
        dropdownTextView.expand(false)
        dropdownTextView.setContentText(
            content.replace(
                "\\n",
                System.getProperty("line.separator")!! + System.getProperty("line.separator")
            )
        )
    }
}