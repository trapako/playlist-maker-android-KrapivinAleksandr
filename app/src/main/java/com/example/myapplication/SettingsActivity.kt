package com.example.myapplication


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                SettingsScreen(onBackClick = { finish() })
            }
        }
    }
}

@Composable
fun SettingsScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onBackClick)
                )
                Spacer(modifier = Modifier.width(32.dp))
                Text(
                    text = "Настройки",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(top = 16.dp)
        ) {
            // Темная тема
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Темная тема", modifier = Modifier.weight(1f), fontSize = 16.sp)
                Switch(checked = false, onCheckedChange = {})
            }

            SettingsItem(text = "Поделиться приложением", icon = Icons.Default.Share)
            SettingsItem(text = "Написать в поддержку", icon = Icons.Default.HeadsetMic)
            SettingsItem(text = "Пользовательское соглашение", icon = Icons.Default.KeyboardArrowRight)
        }
    }
}

@Composable
fun SettingsItem(text: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, modifier = Modifier.weight(1f), fontSize = 16.sp)
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MyApplicationTheme {
        SettingsScreen(onBackClick = {})
    }
}