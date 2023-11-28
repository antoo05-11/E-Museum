package com.example.e_museum.fragments.fragments_inside_museum

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_museum.R
import com.example.e_museum.SQLConnection
import com.example.e_museum.adapters.NotificationListAdapter
import com.example.e_museum.databinding.FragmentNotificationsBinding
import com.example.e_museum.entities.Notification

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notifications = ArrayList<Notification>()
        val adapter = NotificationListAdapter(requireActivity(), notifications, this)
        binding.rvcNotifications.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvcNotifications.adapter = adapter

        binding.loadingNotificationsProgressBar.isVisible = true
        binding.rvcNotifications.isVisible = false

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
            val resultSet =
                SQLConnection.getSqlConnection().getDataQuery("select * from notifications")
            while (resultSet.next()) {
                notifications.add(Notification(resultSet))
            }
            requireActivity().runOnUiThread {
                adapter.notifyDataSetChanged()
                binding.loadingNotificationsProgressBar.isVisible = false
                binding.rvcNotifications.isVisible = true
            }

        }.start()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Museum A"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Museum A"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}