package com.zs.jyoon.musigi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.zs.jyoon.library.LibraryScreen
import com.zs.jyoon.musigi.ui.theme.MusigiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusigiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LibraryScreen()
                }
            }
        }
    }
}
