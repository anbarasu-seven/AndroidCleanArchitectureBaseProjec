package com.example.mvvmhilt.data.models

sealed class Routes(val route: String) {
    object Login : Routes("Login")
    object Shows : Routes("Dashboard")
}