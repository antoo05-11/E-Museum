package com.example.e_museum.fragments.fragments_inside_museum

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.e_museum.R
import com.example.e_museum.databinding.FragmentFindingThingBinding

class ThingFindingFragment : Fragment() {

    private var _binding: FragmentFindingThingBinding? = null
    private val binding get() = _binding!!

    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindingThingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Thread {
            SystemClock.sleep(2000)
//            val intent = Intent(context, MuseumChoosingActivity::class.java).apply {
//                putExtra(EXTRA_MESSAGE, "hello")
//            }
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent)
        }.start()

//        setHasOptionsMenu(true)
//        (activity as AppCompatActivity).supportActionBar?.title = "Museum A"
//        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val thing_finding_nav_view = binding.thingFindingNavView
        thing_finding_nav_view.background = null

        val navView: NavHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_thing_finding) as NavHostFragment
        val navController = navView.navController
        NavigationUI.setupWithNavController(binding.thingFindingNavView, navController)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}