package com.example.e_museum.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_museum.R
import com.example.e_museum.SQLConnection
import com.example.e_museum.adapters.NotificationListAdapter
import com.example.e_museum.adapters.ThingListAdapter
import com.example.e_museum.databinding.FragmentNotificationsBinding
import com.example.e_museum.entities.Museum
import com.example.e_museum.entities.Notification

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notifications = ArrayList<Notification>()
        val adapter = NotificationListAdapter(requireActivity(), notifications)
        binding.rvcNotifications.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvcNotifications.adapter = adapter

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
                Toast.makeText(requireActivity(), notifications.size.toString(), Toast.LENGTH_SHORT)
                    .show()

                adapter.notifyDataSetChanged()
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
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}