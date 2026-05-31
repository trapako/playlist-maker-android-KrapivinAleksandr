package com.example.myapplication.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.network.Track
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.view_model.SearchState
import com.example.myapplication.ui.view_model.SearchViewModel

class SearchActivity : ComponentActivity() {
    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModel.getViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SearchScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = searchViewModel,
                        onBackClick = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
fun TrackListItem(track: Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_music),
            contentDescription = "Трек ${track.trackName}",
            modifier = Modifier
                .size(45.dp)
                .padding(end = 8.dp)
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(track.trackName, fontWeight = FontWeight.Bold)
            Text(track.artistName, color = Color.Gray)
        }
        Column(
            modifier = Modifier.padding(start = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(track.trackTime, color = Color.Gray)
        }
    }
}

@Composable
fun SearchScreen(
    modifier: Modifier,
    viewModel: SearchViewModel,
    onBackClick: () -> Unit
) {
    val screenState by viewModel.searchScreenState.collectAsState()
    SearchScreenContent(
        modifier = modifier,
        screenState = screenState,
        onSearchClick = { query -> viewModel.search(query) },
        onBackClick = onBackClick
    )
}


@Composable
fun SearchScreenContent(
    modifier: Modifier,
    screenState: SearchState,
    onSearchClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
    ) {
        // Верхняя панель с кнопкой Назад и заголовком
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
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
                text = "Поиск",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Поле ввода
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            leadingIcon = {
                Icon(
                    modifier = Modifier.clickable { onSearchClick(text) },
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Поиск") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Состояния экрана
        Box(modifier = modifier.weight(1f)) {
            when (screenState) {
                is SearchState.Initial -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Введите строку для поиска")
                    }
                }

                is SearchState.Searching -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is SearchState.Success -> {
                    val tracks = screenState.foundList
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(tracks.size) { index ->
                            TrackListItem(track = tracks[index])
                            HorizontalDivider(thickness = 0.5.dp)
                        }
                    }
                }

                is SearchState.Fail -> {
                    val error = screenState.error
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Ошибка: $error", color = Color.Red)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Начальный экран")
@Composable
fun SearchScreenInitialPreview() {
    MyApplicationTheme {
        SearchScreenContent(
            modifier = Modifier,
            screenState = SearchState.Initial,
            onSearchClick = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Результаты поиска")
@Composable
fun SearchScreenSuccessPreview() {
    MyApplicationTheme {
        SearchScreenContent(
            modifier = Modifier,
            screenState = SearchState.Success(
                foundList = listOf(
                    Track("Yesterday", "The Beatles", "2:05"),
                    Track("Let It Be", "The Beatles", "4:03")
                )
            ),
            onSearchClick = {},
            onBackClick = {}
        )
    }
}