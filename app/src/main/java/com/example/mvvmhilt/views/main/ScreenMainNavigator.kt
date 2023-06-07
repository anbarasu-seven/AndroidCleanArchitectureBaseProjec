package com.example.mvvmhilt.views.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvvmhilt.data.models.Routes
import com.example.mvvmhilt.views.login.LoginPage
import com.example.mvvmhilt.views.shows.ShowsCompose

@Composable
fun ScreenMainNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Login.route) {

        composable(Routes.Login.route) {
            LoginPage(navController = navController)
        }

        composable(Routes.Shows.route) {
            ShowsCompose(navController = navController)
        }

    }
}