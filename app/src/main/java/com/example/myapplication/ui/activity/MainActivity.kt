package com.example.myapplication.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.view_model.SearchViewModel

enum class PlaylistScreen {
    MAIN,
    SEARCH,
    SETTINGS,
    MEDIATEKA
}

class MainActivity : ComponentActivity() {
    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModel.getViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                PlaylistHost(navController = navController, searchViewModel = searchViewModel)
            }
        }
    }
}

@Composable
fun PlaylistHost(navController: NavHostController, searchViewModel: SearchViewModel) {
    NavHost(
        navController = navController,
        startDestination = PlaylistScreen.MAIN.name
    ) {
        composable(PlaylistScreen.MAIN.name) {
            MainScreen(
                onSearchClick = { navController.navigate(PlaylistScreen.SEARCH.name) },
                onMediatekaClick = { navController.navigate(PlaylistScreen.MEDIATEKA.name) },
                onSettingsClick = { navController.navigate(PlaylistScreen.SETTINGS.name) }
            )
        }
        composable(PlaylistScreen.SEARCH.name) {
            Scaffold { innerPadding ->
                // Используем наш SearchScreen из SearchActivity.kt
                SearchScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = searchViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
        composable(PlaylistScreen.SETTINGS.name) {
            SettingsScreen(onBackClick = { navController.popBackStack() })
        }
        composable(PlaylistScreen.MEDIATEKA.name) {
            MediatekaScreen(onBackClick = { navController.popBackStack() })
        }
    }
}

@Composable
fun MainScreen(
    onSearchClick: () -> Unit,
    onMediatekaClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3772FF))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp)
        ) {
            Text(
                text = "Playlist maker",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(0xFFF3F3F3),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(top = 16.dp)
        ) {
            MenuItem(text = "Поиск", icon = Icons.Default.Search, onClick = onSearchClick)
            MenuItem(text = "Медиатека", icon = Icons.Default.LibraryMusic, onClick = onMediatekaClick)
            MenuItem(text = "Избранное", icon = Icons.Default.FavoriteBorder, onClick = { })
            MenuItem(text = "Настройки", icon = Icons.Default.Settings, onClick = onSettingsClick)
        }
    }
}

@Composable
fun MenuItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp), tint = Color.Black)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, modifier = Modifier.weight(1f), fontSize = 18.sp, color = Color.Black)
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, modifier = Modifier.size(24.dp), tint = Color.Gray)
    }
}