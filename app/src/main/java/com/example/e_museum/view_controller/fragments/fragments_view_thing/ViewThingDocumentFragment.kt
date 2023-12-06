package com.example.e_museum.view_controller.fragments.fragments_view_thing

import android.graphics.Typeface
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.e_museum.view_controller.activities.PlayerViewModel
import com.example.e_museum.databinding.FragmentViewThingDocumentBinding
import com.example.e_museum.entities.Thing
import hakobastvatsatryan.DropdownTextView

class ViewThingDocumentFragment : Fragment() {
    private lateinit var binding: FragmentViewThingDocumentBinding
    private lateinit var playerViewModel: PlayerViewModel

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentViewThingDocumentBinding.inflate(inflater, container, false)

        playerViewModel = ViewModelProvider(requireActivity())[PlayerViewModel::class.java]

        val thing = activity?.intent?.extras?.getSerializable("thing") as Thing

        formatDropdownTextView(binding.originDropdownTextView, thing.origin)
        formatDropdownTextView(binding.ageDropdownTextView, thing.age)
        formatDropdownTextView(binding.thingDescriptionDropdownTextView, thing.description)
        binding.thingDescriptionDropdownTextView.getContentTextView().justificationMode =
            JUSTIFICATION_MODE_INTER_WORD
        binding.originDropdownTextView.getContentTextView().justificationMode =
            JUSTIFICATION_MODE_INTER_WORD
        formatDropdownTextView(binding.thingSizeDropdownTextView, thing.size)

        binding.thingNameTextView.text = thing.name
        return binding.root
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