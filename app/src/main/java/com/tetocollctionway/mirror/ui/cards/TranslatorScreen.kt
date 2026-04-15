package com.tetocollctionway.mirror.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TranslatorScreen(navController: NavController) {
    var inputText by remember { mutableStateOf("") }
    var translatedText by remember { mutableStateOf("") }
    var isTranslated by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF0F172A)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // زر اختيار اللغة (100 لغة) - منتصف علوي
        Button(
            onClick = { /* تفعيل القائمة */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700)),
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text("English ↔ Arabic", color = Color.Black)
        }

        // المحرر العلوي (الإدخال)
        Box(modifier = Modifier.fillMaxWidth().weight(1f).background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(15.dp))) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { 
                    if (isTranslated) { inputText = it; translatedText = ""; isTranslated = false }
                    else { inputText = it }
                },
                modifier = Modifier.fillMaxSize(),
                placeholder = { Text("اكتب أو تحدث...", color = Color.Gray) },
                textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color.Transparent, unfocusedBorderColor = Color.Transparent)
            )
            // مايك أسفل اليسار
            IconButton(
                onClick = { inputText = ""; translatedText = ""; isTranslated = false },
                modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)
            ) {
                Text("🎤", fontSize = 24.sp)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // المحرر السفلي (الترجمة)
        Box(modifier = Modifier.fillMaxWidth().weight(1f).background(Color.White.copy(alpha = 0.08f), RoundedCornerShape(15.dp))) {
            Text(
                text = translatedText,
                modifier = Modifier.fillMaxSize().padding(16.dp),
                color = Color(0xFFFFD700),
                fontSize = 18.sp
            )
            
            // أدوات أسفل اليمين (سبيكر، مشاركة، نسخ)
            Row(modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)) {
                IconButton(onClick = { /* نسخ */ }) { Text("📋") }
                IconButton(onClick = { /* مشاركة صوتية */ }) { Text("📤") }
                IconButton(onClick = { /* نطق */ }) { Text("🔊") }
            }
        }
    }
}
