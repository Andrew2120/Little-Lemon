package com.example.littlelemon

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.presentation.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()
                MyNavigation(navController = navController, sharedPreferences)

            }
        }
    }
}
