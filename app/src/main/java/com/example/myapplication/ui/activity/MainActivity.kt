package com.example.myapplication.ui.activity

import com.example.myapplication.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

enum class PlaylistScreen {
    MAIN,
    SEARCH,
    SETTINGS,
    MEDIATEKA
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                PlaylistHost(navController = navController)
            }
        }
    }
}

@Composable
fun PlaylistHost(navController: NavHostController) {
    fun navigateToSearch() = navController.navigate(PlaylistScreen.SEARCH.name)
    fun navigateToSettings() = navController.navigate(PlaylistScreen.SETTINGS.name)
    fun navigateToMediateka() = navController.navigate(PlaylistScreen.MEDIATEKA.name)
    fun goBack() = navController.popBackStack()

    NavHost(
        navController = navController,
        startDestination = PlaylistScreen.MAIN.name
    ) {
        composable(PlaylistScreen.MAIN.name) {
            MainScreen(
                onSearchClick = { navigateToSearch() },
                onMediatekaClick = { navigateToMediateka() },
                onSettingsClick = { navigateToSettings() }
            )
        }
        composable(PlaylistScreen.SEARCH.name) {
            SearchScreen(onBackClick = { goBack() })
        }
        composable(PlaylistScreen.SETTINGS.name) {
            SettingsScreen(onBackClick = { goBack() })
        }
        composable(PlaylistScreen.MEDIATEKA.name) {
            MediatekaScreen(onBackClick = { goBack() })
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
        // Синяя шапка
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp)
        ) {
            Text(
                text = "Playlist maker",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
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
            MenuItem(
                text = "Поиск",
                icon = Icons.Default.Search,
                onClick = onSearchClick
            )
            MenuItem(
                text = "Медиатека",
                icon = Icons.Default.LibraryMusic,
                onClick = onMediatekaClick
            )
            MenuItem(
                text = "Избранное",
                icon = Icons.Default.FavoriteBorder,
                onClick = { }
            )
            MenuItem(
                text = "Настройки",
                icon = Icons.Default.Settings,
                onClick = onSettingsClick
            )
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
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            fontSize = 18.sp,
            color = Color.Black
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyApplicationTheme {
        MainScreen(
            onSearchClick = {},
            onMediatekaClick = {},
            onSettingsClick = {}
        )
    }
}