package com.example.e_museum.activities

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
import com.example.e_museum.MainActivity
import com.example.e_museum.R
import com.example.e_museum.adapters.MuseumListAdapter
import com.example.e_museum.databinding.ActivityChoosingMuseumBinding
import com.example.e_museum.entities.Museum


class MuseumChoosingActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityChoosingMuseumBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityChoosingMuseumBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        (this as AppCompatActivity).supportActionBar!!.title = "Chọn bảo tàng"

        val museums = ArrayList<Museum>()
        museums.add(Museum())
        museums.add(Museum())
        museums.add(Museum())
        museums.add(Museum())
        museums.add(Museum())
        val tmpMuseumListAdapter = MuseumListAdapter(this, museums)
        viewBinding.rcvMuseums.layoutManager = LinearLayoutManager(this)
        viewBinding.rcvMuseums.adapter = tmpMuseumListAdapter

        val dividerItemDecoration = DividerItemDecoration(
            viewBinding.rcvMuseums.context,
            (viewBinding.rcvMuseums.layoutManager as LinearLayoutManager).orientation
        )
        ContextCompat.getDrawable(
            applicationContext,
            R.drawable.divider_shape
        )?.let {
            dividerItemDecoration.setDrawable(
                it
            )
        }
        viewBinding.rcvMuseums.addItemDecoration(dividerItemDecoration)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewBinding.searchView.windowToken, 0)

        Thread {
            SystemClock.sleep(2000)
            val resultSet = MainActivity.sqlConnection.getDataQuery("select * from museums");
            museums.clear()
            while (resultSet.next()) {
                museums.add(Museum(resultSet))
            }
            runOnUiThread {
                val museumListAdapter = MuseumListAdapter(this, museums)
                viewBinding.rcvMuseums.layoutManager = LinearLayoutManager(this)
                viewBinding.rcvMuseums.adapter = museumListAdapter

                viewBinding.searchView.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        museumListAdapter.filter.filter(query)
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        museumListAdapter.filter.filter(newText)
                        return true
                    }
                })
            }
        }.start()
    }
}


