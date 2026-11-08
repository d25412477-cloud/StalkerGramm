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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val bg = Color(0xFF10140F)
val panel = Color(0xFF20271C)
val green = Color(0xFF9BAF42)
val white = Color(0xFFE4E8D9)
val gray = Color(0xFF929987)

data class Chat(
    val name: String,
    val message: String,
    val time: String,
    val icon: String
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
    var selected by remember { mutableStateOf<Chat?>(null) }
    var message by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<String>()) }

    val chats = listOf(
        Chat("Сталкер", "Есть новости с Кордона?", "16:20", "☢"),
        Chat("Бар 100 Рентген", "Заходи, поговорим", "15:48", "🍺"),
        Chat("Долг", "Задание выполнено", "14:32", "🛡"),
        Chat("Свобода", "Увидимся на базе", "12:10", "🌿"),
        Chat("Неизвестный", "Ты где сейчас?", "Вчера", "?")
    )

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = bg
        ) {
            if (selected == null) {
                Column {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(panel)
                            .padding(18.dp)
                    ) {
                        Text(
                            "STALKERGRAM",
                            color = green,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "СВЯЗЬ С ЗОНОЙ",
                            color = gray,
                            fontSize = 11.sp
                        )
                    }

                    Text(
                        "ПЕРЕПИСКИ",
                        color = green,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(chats) { chat ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selected = chat
                                        messages = emptyList()
                                    }
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .background(panel, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(chat.icon, fontSize = 25.sp)
                                }

                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 14.dp)
                                ) {
                                    Text(
                                        chat.name,
                                        color = white,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        chat.message,
                                        color = gray,
                                        fontSize = 13.sp
                                    )
                                }

                                Text(
                                    chat.time,
                                    color = gray,
                                    fontSize = 11.sp
                                )
                            }
                            HorizontalDivider(color = panel)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(panel)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text("💬 Чаты", color = green)
                        Text("☢ Зона", color = gray)
                        Text("👤 Профиль", color = gray)
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(panel)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "‹",
                            color = green,
                            fontSize = 32.sp,
                            modifier = Modifier.clickable {
                                selected = null
                            }
                        )
                        Text(
                            selected!!.name,
                            color = white,
                            fontSize = 19.sp,
                            modifier = Modifier.padding(start = 18.dp)
                        )
                    }

                    LazyColumn(
                        modifier = Modifier.weight(1f).padding(12.dp)
                    ) {
                        items(messages) { item ->
                            Text(
                                item,
                                color = white,
                                modifier = Modifier
                                    .padding(6.dp)
                                    .background(panel, RoundedCornerShape(12.dp))
                                    .padding(12.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(panel)
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = message,
                            onValueChange = { message = it },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("Сообщение...") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = panel,
                                unfocusedContainerColor = panel,
                                focusedTextColor = white,
                                unfocusedTextColor = white
                            )
                        )

                        Text(
                            "➤",
                            color = green,
                            fontSize = 25.sp,
                            modifier = Modifier
                                .clickable {
                                    if (message.isNotBlank()) {
                                        messages = messages + message
                                        message = ""
                                    }
                                }
                                .padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}
