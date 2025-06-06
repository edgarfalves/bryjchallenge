package com.example.bryjchallenge.ui.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.bryjchallenge.data.ErrorMessage
import kotlinx.coroutines.flow.StateFlow


@Composable
fun HomeScreen(uiState: StateFlow<MainScreenUIState>, itemClickFunction: (String) -> Unit) {

    val uiStateCollect = uiState.collectAsState().value
    val isLoading = uiStateCollect.isLoading
    var listFeed: List<String>
    var errorMessages: List<ErrorMessage>
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (isLoading) {
                LoadingContent()
            } else {
                when (uiStateCollect) {
                    is MainScreenUIState.HasFeed -> {
                        listFeed = uiStateCollect.listFeed
                        VerticalList(listFeed, itemClickFunction)
                    }

                    is MainScreenUIState.NoFeed -> {
                        errorMessages = uiStateCollect.errorMessages
                        ErrorVerticalList(errorMessages)
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingContent() {
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Composable
fun VerticalList(itemList: List<String>, itemClickFunction: (String) -> Unit) {
    LazyColumn {
        items(itemList) { item ->
            Text(
                text = item,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clickable { itemClickFunction(item) },
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun ErrorVerticalList(itemList: List<ErrorMessage>) {
    LazyColumn {
        items(itemList) { item ->
            Text(
                text = item.message,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                style = TextStyle(
                    color = Color.Red
                )
            )
        }
    }
}