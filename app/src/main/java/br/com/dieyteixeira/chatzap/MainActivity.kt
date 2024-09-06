package br.com.dieyteixeira.chatzap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dieyteixeira.chatzap.features.chatsList.ChatsListScreen
import br.com.dieyteixeira.chatzap.features.dateSet.DateScreen
import br.com.dieyteixeira.chatzap.ui.theme.ChatZapTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatZapTheme {
                App()
            }
        }
    }
}

class BottomAppBarItem(
    val icon: ImageVector,
    val label: String
)

class TopAppBarItem(
    val title: String,
    val icons: List<ImageVector> = emptyList()
)

sealed class ScreenItem(
    val topAppBarItem: TopAppBarItem,
    val bottomAppBarItem: BottomAppBarItem
) {
    data object Chats : ScreenItem(
        topAppBarItem = TopAppBarItem(
            title = "Chats",
            icons = listOf(
                Icons.Default.AccountBox,
                Icons.Default.MoreVert
            )
        ),
        bottomAppBarItem = BottomAppBarItem(
            icon = Icons.Default.Email,
            label = "Chats"
        )
    )
    data object Updates : ScreenItem(
        topAppBarItem = TopAppBarItem(
            title = "Updates",
            icons = listOf(
                Icons.Default.AccountBox,
                Icons.Default.Search,
                Icons.Default.MoreVert
            )
        ),
        bottomAppBarItem = BottomAppBarItem(
            icon = Icons.Default.CheckCircle,
            label = "Updates"
        )
    )
    data object Comunities : ScreenItem(
        topAppBarItem = TopAppBarItem(
            title = "Comunities",
            icons = listOf(
                Icons.Default.AccountBox,
                Icons.Default.MoreVert
            )
        ),
        bottomAppBarItem = BottomAppBarItem(
            icon = Icons.Default.Person,
            label = "Comunities"
        )
    )
    data object Calls : ScreenItem(
        topAppBarItem = TopAppBarItem(
            title = "Calls",
            icons = listOf(
                Icons.Default.AccountBox,
                Icons.Default.Search,
                Icons.Default.MoreVert
            )
        ),
        bottomAppBarItem = BottomAppBarItem(
            icon = Icons.Default.Call,
            label = "Calls"
        )
    )
    data object Date : ScreenItem(
        topAppBarItem = TopAppBarItem(
            title = "Date"
        ),
        bottomAppBarItem = BottomAppBarItem(
            icon = Icons.Default.DateRange,
            label = "Date"
        )
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun App() {
    val screens = remember {
        listOf(
            ScreenItem.Chats,
            ScreenItem.Updates,
            ScreenItem.Comunities,
            ScreenItem.Calls,
            ScreenItem.Date
        )
    }
    var currentScreen by remember {
        mutableStateOf(screens.first())
    }
    val pagerState = rememberPagerState {
        screens.size
    }
    LaunchedEffect(currentScreen){
        pagerState.animateScrollToPage(
            screens.indexOf(currentScreen)
        )
    }
    LaunchedEffect(pagerState.targetPage, screens) {
        currentScreen = screens[pagerState.targetPage]
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(currentScreen.topAppBarItem.title)
            }, actions = {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    currentScreen.topAppBarItem.icons.forEach { icon ->
                        Icon(icon, contentDescription = null)
                    }
                }
            })
        },
        bottomBar = {
            BottomAppBar {
                screens.forEach { screen ->
                    with(screen.bottomAppBarItem) {
                        NavigationBarItem(
                            selected = screen == currentScreen,
                            onClick = { currentScreen = screen },
                            icon = { Icon(icon, contentDescription = null) },
                            label = { Text(label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            pagerState,
            Modifier.padding(innerPadding)
        ) { page ->
            val item = screens[page]
            when(item) {
                ScreenItem.Chats -> ChatsListScreen()
                ScreenItem.Updates -> UpdatesScreen()
                ScreenItem.Comunities -> ComunitiesScreen()
                ScreenItem.Calls -> CallScreen()
                ScreenItem.Date -> DateScreen()
            }
        }
    }
}

@Composable
fun UpdatesScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Text(
            "Update",
            Modifier.align(Alignment.Center),
            style = TextStyle.Default.copy(
                fontSize = 32.sp
            )
        )
    }
}

@Composable
fun ComunitiesScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Text(
            "Comunities",
            Modifier.align(Alignment.Center),
            style = TextStyle.Default.copy(
                fontSize = 32.sp
            )
        )
    }
}

@Composable
fun CallScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Text(
            "Calls",
            Modifier.align(Alignment.Center),
            style = TextStyle.Default.copy(
                fontSize = 32.sp
            )
        )
    }
}

@Preview
@Composable
private fun AppPreview() {
    ChatZapTheme{
        App()
    }
}