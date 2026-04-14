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
    var translatedText by remember { mutableStateOf("") }
    
    // متغيرات الـ Logic
    var isRecording by remember { mutableStateOf(false) }
    var isSpeaking by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // المربع العلوي مع المايك
        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.fillMaxSize(),
                label = { Text("اكتب أو تحدث...", color = Color.White) },
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFFFD700))
            )
            IconButton(
                onClick = { 
                    isRecording = !isRecording
                    if (isRecording) isSpeaking = false // طفي السماعة لو المايك اشتغل
                },
                modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)
            ) {
                // تغيير الأيقونة واللون للتشغيل
                Text(if (isRecording) "🔴" else "🎤", fontSize = 28.sp, color = if(isRecording) Color.Red else Color.White)
            }
        }

        // زرار اللغة
        Button(
            onClick = { /* كود الترجمة الفوري */ },
            modifier = Modifier.padding(vertical = 10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
        ) {
            Text("English ↔ Arabic", color = Color.Black)
        }

        // المربع السفلي مع السماعة
        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            OutlinedTextField(
                value = translatedText,
                onValueChange = {},
                modifier = Modifier.fillMaxSize(),
                label = { Text("الترجمة", color = Color(0xFFFFD700)) },
                readOnly = true,
                textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFFFFD700)),
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color(0xFFFFD700))
            )
            IconButton(
                onClick = { 
                    isSpeaking = !isSpeaking
                    if (isSpeaking) isRecording = false // طفي المايك لو السماعة اشتغلت
                },
                modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)
            ) {
                // تغيير اللون للتشغيل
                Text(if (isSpeaking) "🛑" else "🔊", fontSize = 28.sp, color = if(isSpeaking) Color(0xFFFFD700) else Color.White)
            }
        }
    }
}
