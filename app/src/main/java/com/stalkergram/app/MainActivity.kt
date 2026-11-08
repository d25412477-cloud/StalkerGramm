package com.stalkergram.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF10140F)
private val Panel = Color(0xFF1B2118)
private val Green = Color(0xFF9BAF45)
private val White = Color(0xFFE8EBDD)
private val Gray = Color(0xFF929B89)

data class Chat(
    val name: String,
    val message: String,
    val time: String,
    val unread: Int,
    val avatar: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StalkerGram()
        }
    }
}

@Composable
fun StalkerGram() {
    val chats = remember {
        listOf(
            Chat("Сидорович", "Есть работа для тебя.", "16:42", 2, "С"),
            Chat("Бар 100 Рентген", "Долг: заходи вечером.", "15:30", 0, "☢"),
            Chat("Долг", "Группировка собирается.", "14:18", 5, "Д"),
            Chat("Свобода", "Увидимся на Свалке.", "13:05", 0, "С"),
            Chat("Бродяга", "Ты где сейчас?", "12:40", 1, "Б"),
            Chat("Торговец", "Товар поступил.", "Вчера", 0, "Т")
        )
    }

    var selectedChat by remember { mutableStateOf<Chat?>(null) }
    var tab by remember { mutableStateOf("Чаты") }
    var search by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Bg
        ) {
            if (selectedChat != null) {
                ChatScreen(
                    chat = selectedChat!!,
                    onBack = { selectedChat = null }
                )
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(Panel)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { tab = "Меню" }) {
                            Icon(Icons.Default.Menu, null, tint = White)
                        }

                        if (search) {
                            OutlinedTextField(
                                value = query,
                                onValueChange = { query = it },
                                placeholder = { Text("Поиск", color = Gray) },
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Text(
                                "StalkerGram",
                                color = White,
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        IconButton(onClick = {
                            search = !search
                            query = ""
                        }) {
                            Icon(Icons.Default.Search, null, tint = Green)
                        }
                    }

                    when (tab) {
                        "Чаты" -> {
                            LazyColumn(modifier = Modifier.weight(1f)) {
                                items(chats.filter {
                                    it.name.contains(query, true) ||
                                    it.message.contains(query, true)
                                }) { chat ->
                                    ChatItem(chat) {
                                        selectedChat = chat
                                    }
                                }
                            }
                        }

                        else -> {
                            Box(
                                modifier = Modifier.weight(1f).fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    if (tab == "Меню") "Меню StalkerGram"
                                    else tab,
                                    color = White,
                                    fontSize = 22.sp
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(62.dp)
                            .background(Panel),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BottomItem("Контакты", Icons.Default.Person, tab) { tab = "Контакты" }
                        BottomItem("Звонки", Icons.Default.Call, tab) { tab = "Звонки" }
                        BottomItem("Чаты", Icons.Default.Chat, tab) { tab = "Чаты" }
                        BottomItem("Настройки", Icons.Default.Settings, tab) { tab = "Настройки" }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatItem(chat: Chat, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .background(Green, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                chat.avatar,
                color = Bg,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(chat.name, color = White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(5.dp))
            Text(chat.message, color = Gray, fontSize = 14.sp, maxLines = 1)
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(chat.time, color = Gray, fontSize = 11.sp)
            if (chat.unread > 0) {
                Spacer(Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .background(Green, CircleShape)
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Text(chat.unread.toString(), color = Bg, fontSize = 11.sp)
                }
            }
        }
    }

    HorizontalDivider(color = Panel, thickness = 1.dp)
}

@Composable
fun BottomItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon,
            null,
            tint = if (selected == title) Green else Gray
        )
        Text(
            title,
            color = if (selected == title) Green else Gray,
            fontSize = 10.sp
        )
    }
}

@Composable
fun ChatScreen(chat: Chat, onBack: () -> Unit) {
    var message by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.fillMaxSize().background(Bg)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .background(Panel)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, null, tint = White)
            }
            Column {
                Text(chat.name, color = White, fontWeight = FontWeight.Bold)
                Text("был недавно", color = Gray, fontSize = 11.sp)
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f).padding(12.dp)
        ) {
            item {
                Text(
                    "Это начало переписки",
                    color = Gray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
            items(messages) { msg ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .background(Green, RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ) {
                        Text(msg, color = Bg)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Panel)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Сообщение...", color = Gray) },
                singleLine = true
            )

            IconButton(onClick = {
                if (message.isNotBlank()) {
                    messages.add(message.trim())
                    message = ""
                }
            }) {
                Icon(Icons.Default.Send, null, tint = Green)
            }
        }
    }
}
