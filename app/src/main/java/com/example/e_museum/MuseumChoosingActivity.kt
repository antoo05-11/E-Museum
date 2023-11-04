package com.example.e_museum

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_museum.adapters.MuseumListAdapter
import com.example.e_museum.databinding.MuseumChoosingViewBinding
import com.example.e_museum.entities.Museum


class MuseumChoosingActivity : AppCompatActivity() , View.OnClickListener {
    private lateinit var viewBinding: MuseumChoosingViewBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = MuseumChoosingViewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        (this as AppCompatActivity).supportActionBar!!.title = "Chọn bảo tàng"

        val museums = ArrayList<Museum>()


        Thread {
            val resultSet = MainActivity.sqlConnection.getDataQuery("select * from museums");
            while (resultSet.next()) {
                museums.add(Museum(resultSet))
            }
            runOnUiThread {
                val museumListAdapter = MuseumListAdapter(this, museums)
                viewBinding.rcvMuseums.layoutManager = LinearLayoutManager(this)
                viewBinding.rcvMuseums.adapter = museumListAdapter

                val itemDecoration: RecyclerView.ItemDecoration =
                    DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
                viewBinding.rcvMuseums.addItemDecoration(itemDecoration)

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
                };
                viewBinding.rcvMuseums.addItemDecoration(dividerItemDecoration)

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
        (this.applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            viewBinding.searchView.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    override fun onClick(v: View) {
        if (v.id == viewBinding.searchView.id) {
            val inm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}



