package com.example.e_museum.view_controller.fragments.fragments_inside_museum

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_museum.R
import com.example.e_museum.view_controller.adapters.NotificationsListAdapter
import com.example.e_museum.databinding.FragmentNotificationsBinding
import com.example.e_museum.entities.Notification
import com.example.e_museum.data_fetching.models.Model
import com.example.e_museum.entities.Museum

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val museum = requireActivity().intent.getSerializableExtra("museum") as Museum

        val notifications = ArrayList<Notification>()
        val adapter =
            NotificationsListAdapter(
                requireActivity(),
                notifications,
            )
        binding.rvcNotifications.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvcNotifications.adapter = adapter

        notifications.add(Notification())
        notifications.add(Notification())
        notifications.add(Notification())
        notifications.add(Notification())
        notifications.add(Notification())

        val dividerItemDecoration = DividerItemDecoration(
            binding.rvcNotifications.context,
            (binding.rvcNotifications.layoutManager as LinearLayoutManager).orientation
        )
        ContextCompat.getDrawable(
            requireActivity().applicationContext,
            R.drawable.divider_shape
        )?.let {
            dividerItemDecoration.setDrawable(
                it
            )
        }
        binding.rvcNotifications.addItemDecoration(dividerItemDecoration)

        Thread {
            adapter.setNotifications(
                Model.getInstance().notificationModel.getNotificationsByMuseumID(
                    museum.museumID
                )
            )
            activity?.runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }.start()

        binding.notiSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}