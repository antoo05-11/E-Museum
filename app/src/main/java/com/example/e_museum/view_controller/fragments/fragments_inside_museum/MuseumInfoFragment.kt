package com.example.e_museum.view_controller.fragments.fragments_inside_museum

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.e_museum.view_controller.activities.MainActivity
import com.example.e_museum.view_controller.adapters.MuseumImagesListAdapter
import com.example.e_museum.databinding.FragmentMuseumInfoBinding
import com.example.e_museum.entities.Museum
import java.util.Timer
import java.util.TimerTask

private const val DELAY_MS: Long = 500
private const val PERIOD_MS: Long = 3000

class MuseumInfoFragment : Fragment() {
    private lateinit var binding: FragmentMuseumInfoBinding

    private lateinit var adapter: MuseumImagesListAdapter
    private lateinit var museumImagesViewPager: ViewPager2

    private var currentPage = 0
    private var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMuseumInfoBinding.inflate(inflater, container, false);

        val museum = activity?.intent?.getSerializableExtra("museum") as Museum

        museumImagesViewPager = binding.museumImagesViewPager
        binding.museumNameTextView.text = museum.name
        val museumImageURLsList = List(museum.imagesNum) { index ->
            buildString {
                append(MainActivity.fileServerURL)
                append("museum_images/")
                append("${museum.museumID}_${index + 1}.png")
            }
        }

        adapter =
            MuseumImagesListAdapter(
                activity,
                listOf("")
            )
        binding.museumImagesViewPager.adapter = adapter
        binding.museumImagesViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.addressTextView.text = museum.address
        binding.addressTextView.setOnClickListener {
            val address = binding.addressTextView.text
            val content = "geo:0,0?q=$address"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(content))
            startActivity(intent)
        }

        binding.museumDescriptionTextView.text = museum.description
        binding.websiteTextView.text = museum.website
        binding.adultTicketTextView.text = museum.adult_ticket
        binding.childOver6TicketTextView.text = museum.child_over_6_ticket
        binding.childUnder6TicketTextView.text = museum.child_under_6_ticket
        binding.openTimeTextView.text = museum.open_time
        binding.phoneNumberTextView.text = museum.phoneNumber
        binding.theDisableTicketTextView.text = museum.the_disabled_ticket

        Thread {
            Thread.sleep(1000)
            adapter =
                MuseumImagesListAdapter(
                    activity,
                    museumImageURLsList
                )
            activity?.runOnUiThread {
                binding.museumImagesViewPager.adapter = adapter
                val handler = Handler()
                val update = Runnable {
                    if (currentPage == adapter.itemCount) {
                        currentPage = 0
                    }
                    museumImagesViewPager.setCurrentItem(currentPage++, true)
                }
                timer = Timer()
                timer?.schedule(object : TimerTask() {
                    override fun run() {
                        handler.post(update)
                    }
                }, DELAY_MS, PERIOD_MS)
            }
        }.start()

        return binding.root
    }

}