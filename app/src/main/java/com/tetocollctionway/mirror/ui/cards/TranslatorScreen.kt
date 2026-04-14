package com.tetocollctionway.mirror.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TranslatorScreen(navController: NavController) {
    var inputText by remember { mutableStateOf("") }
    var translatedText by remember { mutableStateOf("الترجمة ستظهر هنا...") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier.fillMaxWidth().weight(1f),
            label = { Text("اكتب النص هنا...", color = Color.White) },
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFFD700),
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color(0xFFFFD700)
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* تفعيل المايك قريباً */ }) {
                Text("🎤", fontSize = 24.sp)
            }
            
            Button(
                onClick = { translatedText = "جاري الترجمة..." },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
            ) {
                Text("English ↔ Arabic", color = Color.Black)
            }

            IconButton(onClick = { /* تفعيل السماعة قريباً */ }) {
                Text("🔊", fontSize = 24.sp)
            }
        }

        OutlinedTextField(
            value = translatedText,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth().weight(1f),
            label = { Text("الترجمة", color = Color(0xFFFFD700)) },
            readOnly = true,
            textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFFFFD700)),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFFD700),
                unfocusedBorderColor = Color(0xFFFFD700)
            )
        )
    }
}
