package com.example.newsviewer.appscreen

sealed class Screen (val title: String, val route: String) {
    object Home : Screen("Home", "home")
    object Account : Screen("Account", "account")
    object Help : Screen( "Help", "help")
}

val screens = listOf(
    Screen.Home,
    Screen.Account,
    Screen.Help
)

