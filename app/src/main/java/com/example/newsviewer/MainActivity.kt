package com.example.newsviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsviewer.appscreen.AppScreen
import com.example.newsviewer.appscreen.tabviews.TabScreenOne
import com.example.newsviewer.appscreen.tabviews.TabScreenTwo
import com.example.newsviewer.ui.theme.NewsViewerTheme
import com.example.newsviewer.ui.theme.TabColorOne
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsViewerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        AppScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Toolbar(onDrawerClicked: () -> Unit) {
    SearchBar(
        backgroundColor = TabColorOne,
        navigationIcon = {
            IconButton(onClick = {onDrawerClicked()}) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    )

//    TopAppBar(
//        title = {
//            Text(
//                text = "News Viewer",
//                color = Color.White
//            )
//        },
//        backgroundColor = TabColorOne,
//        navigationIcon = {
//            IconButton(onClick = {onDrawerClicked()}) {
//                Icon(
//                    Icons.Filled.Menu,
//                    contentDescription = "",
//                    tint = Color.White
//                )
//            }
//        }
//    )
}

@Composable
fun SearchBar(
    backgroundColor: Color,
    navigationIcon: @Composable() () -> Unit,
) {
    var query by remember { mutableStateOf(TextFieldValue("")) }
    Row(
        Modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor
            )
    ){
        navigationIcon()
        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Search", color = Color.White)
            },
            value = query,
            onValueChange = { newValue ->
                query = newValue
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            trailingIcon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "",
                    tint = Color.White
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = TabColorOne,
                cursorColor = TabColorOne,
                placeholderColor = TabColorOne
            )
        )
    }
}

@Composable
fun Home(openDrawer: () -> Unit) {
    Column {
        Toolbar { openDrawer() }
        TabScreen()
    }
}

@Composable
fun Account(openDrawer: () -> Unit) {
    Column {
        Toolbar { openDrawer() }
        TabScreen()
    }
}

@Composable
fun Help(openDrawer: () -> Unit) {
    Column {
        Toolbar { openDrawer() }
        TabScreen()
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabScreen() {
    val pagerState = rememberPagerState(initialPage = 0)
    Column(modifier = Modifier.background(Color.White)) {
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(pagerState: PagerState) {
    HorizontalPager(state = pagerState, count = 2) { page ->
        when (page){
            0 -> TabScreenOne("tab 0")
            1 -> TabScreenTwo("tab 1")
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(pagerState: PagerState) {
    val nameList = listOf("Home","Emails")
    val iconList = listOf(
        Icons.Filled.Home,
        Icons.Filled.Email
    )
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = TabColorOne,
        contentColor = Color.White,
        divider = { TabRowDefaults.Divider(thickness = 2.dp, color = Color.White) },
        indicator = {tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp
            )
        }
    ) {
        nameList.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        nameList[index],
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    ) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                icon = {Icon(iconList[index],"", tint = if (pagerState.currentPage == index) Color.White else Color.LightGray)}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsViewerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column {
                Toolbar{}
                TabScreen()
            }
        }
    }
}