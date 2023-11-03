package com.example.e_museum

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.marginEnd
import androidx.core.view.updateLayoutParams
import coil.compose.AsyncImage
import com.example.e_museum.MainActivity.Companion.sqlConnection
import com.example.e_museum.databinding.MuseumChoosingViewBinding
import com.example.e_museum.ui.theme.EMuseumTheme
import com.squareup.picasso.Picasso
import java.sql.ResultSet


class MuseumChoosingActivity : ComponentActivity() {
    private lateinit var viewBinding: MuseumChoosingViewBinding

    private fun showNotification(content: String) {
        Toast.makeText(applicationContext, content, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = MuseumChoosingViewBinding.inflate(layoutInflater)

        //NOT USE JETPACK COMPOSE
//        setContentView(viewBinding.root)
//        viewBinding.museumListBox.gravity = Gravity.CENTER_HORIZONTAL
//        viewBinding.backButton.setOnClickListener {
//            finish()
//        }
//
//        Thread {
//            val resultSet: ResultSet = sqlConnection.getDataQuery("select * from museums")
//            runOnUiThread {
//                while (resultSet.next()) {
//                    val museumBox = LinearLayout(applicationContext)
//                    museumBox.layoutMode = LinearLayout.HORIZONTAL
//
//                    val museumImage = ImageView(applicationContext)
//                    Picasso.get()
//                        .load("https://toquoc.mediacdn.vn/Uploaded/minhkhanh/2017_07_27/BIN_6105_DVJJ.jpg")
//                        .fit()
//                        .centerInside()
//                        .into(museumImage)
//                    museumBox.addView(museumImage, 0)
//                    museumImage.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
//                    museumImage.layoutParams.width = 300
//
//                    val museumInfoBox = LinearLayout(applicationContext)
//                    museumInfoBox.layoutMode = LinearLayout.VERTICAL
//                    museumBox.addView(museumInfoBox, 1)
//
//                    val museumNameTextView = TextView(applicationContext)
//                    museumInfoBox.addView(museumNameTextView)
//                    museumNameTextView.text = resultSet.getString("name")
//                    museumNameTextView.gravity = Gravity.CENTER
//
//                    val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        300
//                    )
//
//                    params.setMargins(50, 0, 50, 30)
//
//                    museumBox.layoutParams = params
//
//                    val border = GradientDrawable()
//                    border.setColor(-0x1)
//                    border.setStroke(10, -0x1000000)
//                    museumBox.background = border
//                    museumBox.gravity = Gravity.CENTER_VERTICAL
//                    museumBox.setPadding(30, 30, 0, 30);
//                    viewBinding.museumListBox.addView(museumBox)
//                }
//            }
//        }.start()

        setContent {
            EMuseumTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    TopBarApp()
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        EMuseumTheme {
            Greeting("Android")
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun TopBarApp() {
        val context = LocalContext.current
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Small Top App Bar")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                // Xử lý khi người dùng nhấn nút quay lại
                                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    }
                )
            },
        ) { innerPadding ->
            ScrollContent(innerPadding)
        }
    }

    @Composable
    fun ScrollContent(innerPadding: PaddingValues) {
//        Thread {
//            val resultSet: ResultSet = sqlConnection.getDataQuery("select * from museums")
//
//        }.start()
        val range = 1..100

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(range.count()) { index ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = "https://toquoc.mediacdn.vn/Uploaded/minhkhanh/2017_07_27/BIN_6105_DVJJ.jpg",
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )

                    Text(text = "Mục số ${index + 1}")
                }
            }
        }
    }
}



