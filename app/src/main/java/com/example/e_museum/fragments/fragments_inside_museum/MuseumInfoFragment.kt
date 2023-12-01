package com.example.e_museum.fragments.fragments_inside_museum

import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.e_museum.activities.MainActivity
import com.example.e_museum.adapters.MuseumImagesListAdapter
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
        binding = FragmentMuseumInfoBinding.inflate(inflater, container, false)

        museumImagesViewPager = binding.museumImagesViewPager

        val museum = activity?.intent?.getSerializableExtra("museum") as Museum
        val museumImageURLsList = mutableListOf<String>()

        for (i in 1..museum.imagesNum) {
            museumImageURLsList.add(
                String.format(
                    MainActivity.fileServerURL + "museum_images/%d_%d.png",
                    museum.museumID, i
                )
            )
        }

        adapter = MuseumImagesListAdapter(activity, listOf(""))
        binding.museumImagesViewPager.adapter = adapter
        binding.museumImagesViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        Thread {
            Thread.sleep(1000)
            adapter = MuseumImagesListAdapter(activity, museumImageURLsList)
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