package com.example.littlelemon

import android.content.SharedPreferences
import android.service.autofill.UserData
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.MyDestinations.home
import com.example.littlelemon.MyDestinations.onboarding
import com.example.littlelemon.MyDestinations.profile
import com.example.littlelemon.domain.entities.User
import com.example.littlelemon.presentation.pages.home.HomeScreen
import com.example.littlelemon.presentation.pages.onboarding.OnBoardingScreen
import com.example.littlelemon.presentation.pages.profile.ProfileScreen
import com.google.gson.Gson


@Composable
fun MyNavigation(
    navController: NavHostController,
    sharedPreferences: SharedPreferences
) {
    val gson: Gson = Gson()
    val userDataJson = sharedPreferences.getString("User", null)
    val user = gson.fromJson(userDataJson, User::class.java)

    val startDestination =
        determineStartDestination(
            user != null
        )

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = onboarding) {
            OnBoardingScreen(sharedPreferences) {
                navController.navigate(home)

            }
        }
        composable(route = home) {
            HomeScreen()
        }
        composable(route = profile) {
            ProfileScreen(sharedPreferences, {})
        }
    }
}

private fun determineStartDestination(userExists: Boolean): String {
    return if (userExists) home else onboarding
}

