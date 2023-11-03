package com.example.e_museum

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.e_museum.databinding.MuseumChoosingViewBinding

class MuseumChoosingActivity : ComponentActivity() {
    private lateinit var viewBinding: MuseumChoosingViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = MuseumChoosingViewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.backButton.setOnClickListener {
            finish()
        }

        //        setContent {
//            EMuseumTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                    TopBarApp()
//                }
//            }
//        }
//    }
//
//    @Composable
//    fun Greeting(name: String, modifier: Modifier = Modifier) {
//        Text(
//            text = "Hello $name!",
//            modifier = modifier
//        )
//    }
//
//    @Preview(showBackground = true)
//    @Composable
//    fun GreetingPreview() {
//        EMuseumTheme {
//            Greeting("Android")
//        }
//    }
//
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Preview
//    @Composable
//    fun TopBarApp() {
//        val context = LocalContext.current
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    colors = TopAppBarDefaults.smallTopAppBarColors(
//                        containerColor = MaterialTheme.colorScheme.primaryContainer,
//                        titleContentColor = MaterialTheme.colorScheme.primary,
//                    ),
//                    title = {
//                        Text("Small Top App Bar")
//                    },
//                    navigationIcon = {
//                        IconButton(
//                            onClick = {
//                                // Xử lý khi người dùng nhấn nút quay lại
//                                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
//                                finish()
//                            }
//                        ) {
//                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
//                        }
//                    }
//                )
//            },
//        ) { innerPadding ->
//            ScrollContent(innerPadding)
//        }
//    }
//
//    @Composable
//    fun ScrollContent(innerPadding: PaddingValues) {
//        val range = 1..100
//
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize(),
//            contentPadding = innerPadding,
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(range.count()) { index ->
//                Text(text = "- List item number ${index + 1}")
//            }
//        }
    }
}



