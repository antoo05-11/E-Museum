package com.example.e_museum.view_controller.activities

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_museum.R
import com.example.e_museum.view_controller.adapters.MuseumsListAdapter
import com.example.e_museum.databinding.ActivityChoosingMuseumBinding
import com.example.e_museum.entities.Museum
import com.example.e_museum.data_fetching.models.Model

class MuseumChoosingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChoosingMuseumBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoosingMuseumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.findWithGPSTextView.setOnClickListener {
            finish()
        }

        (this as AppCompatActivity).supportActionBar!!.title = getString(R.string.choose_museum)

        val museums = ArrayList<Museum>()
        museums.add(Museum())
        museums.add(Museum())
        museums.add(Museum())
        museums.add(Museum())
        museums.add(Museum())
        val tmpMuseumsListAdapter =
            MuseumsListAdapter(
                this,
                museums
            )
        binding.rcvMuseums.layoutManager = LinearLayoutManager(this)
        binding.rcvMuseums.adapter = tmpMuseumsListAdapter

        val dividerItemDecoration = DividerItemDecoration(
            binding.rcvMuseums.context,
            (binding.rcvMuseums.layoutManager as LinearLayoutManager).orientation
        )
        ContextCompat.getDrawable(
            applicationContext,
            R.drawable.divider_shape
        )?.let {
            dividerItemDecoration.setDrawable(
                it
            )
        }
        binding.rcvMuseums.addItemDecoration(dividerItemDecoration)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchView.windowToken, 0)

        Thread {
            SystemClock.sleep(2000)
            museums.clear()
            museums.addAll(Model.getInstance().museumModel.allMuseums)
            runOnUiThread {
                val museumsListAdapter =
                    MuseumsListAdapter(
                        this,
                        museums
                    )
                binding.rcvMuseums.layoutManager = LinearLayoutManager(this)
                binding.rcvMuseums.adapter = museumsListAdapter

                binding.searchView.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        museumsListAdapter.filter.filter(query)
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        museumsListAdapter.filter.filter(newText)
                        return true
                    }
                })
            }
        }.start()
    }
}



