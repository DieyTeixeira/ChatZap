package br.com.dieyteixeira.chatzap.features.chatsList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.dieyteixeira.chatzap.ui.theme.ChatZapTheme
import kotlin.random.Random

@Composable
fun ChatsListScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // search text field
        item {
            Row(
                modifier
                    .clip(CircleShape)
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = null)
                Spacer(Modifier.size(8.dp))
                Text(text = "Search")
            }
            Spacer(Modifier.size(16.dp))
            // chats filter
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val filters = remember {
                    mutableStateListOf("All", "Unread", "Groups")
                }
                filters.forEach { filter ->
                    Box(
                        Modifier
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .padding(16.dp, 8.dp)
                    ) {
                        Text(text = filter)
                    }
                }
            }
        }
        //chats
        items(10) {
            val avatarSize = 54.dp
            val messageUnread = IntRange(0, 20).random()
            Row(
                modifier
                    .fillMaxWidth()
            ) {
                Box(
                    Modifier
                        .size(avatarSize)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
                Spacer(Modifier.size(16.dp))
                Column(
                    Modifier.heightIn(avatarSize),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Chat name")
                        Text(text = "Last message date")
                    }
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            Modifier.weight(1f),
                        ) {
                            Icon(Icons.Default.Done, contentDescription = null)
                            Spacer(Modifier.size(4.dp))
                            Text(text = "Last message")
                        }
                        Box(
                            Modifier
                                .clip(CircleShape)
                                .size(24.dp)
                                .background(Color.Green)
                                ,
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "$messageUnread")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChatsListScreenPreview() {
    ChatZapTheme {
        ChatsListScreen()
    }
}