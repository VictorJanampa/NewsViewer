package com.example.newsviewer.navigation

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsviewer.Account
import com.example.newsviewer.Help
import com.example.newsviewer.Home
import com.example.newsviewer.composables.Drawer
import kotlinx.coroutines.launch

@Composable
fun AppScreen() {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colors.background) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val openDrawer = {
            scope.launch {
                drawerState.open()
            }
        }
        ModalDrawer(drawerState = drawerState, gesturesEnabled = drawerState.isOpen, drawerContent = {
            Drawer(
                onDestinationClicked = { route ->
                    scope.launch {
                        drawerState.close()
                    }
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                        restoreState = true
                        }
                    }
                )
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route
            ) {
                composable(Screen.Home.route) {
                    Home(
                        openDrawer = {
                            openDrawer()
                        }
                    )
                }
                composable(Screen.Account.route) {
                    Account(
                        openDrawer = {
                            openDrawer()
                        }
                    )
                }
                composable(Screen.Help.route) {
                    Help(
                        openDrawer = {
                            openDrawer()
                        }
                    )
                }
            }

        }
    }
}

