package com.tetocollctionway.mirror.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
    
    // إدارة حالة الأدوات (Logic)
    var isMicActive by remember { mutableStateOf(false) }
    var isSpeakerActive by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E)) // نفس روح الخلفية الجديدة
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // حقل الإدخال
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(1.dp, Color(0xFF42A5F5), RoundedCornerShape(15.dp)),
            label = { Text("اكتب أو تحدث للترجمة...", color = Color.Gray) },
            textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
            trailingIcon = {
                IconButton(onClick = { 
                    isMicActive = !isMicActive
                    if (isMicActive) isSpeakerActive = false // منع التداخل
                }) {
                    Text(
                        text = if (isMicActive) "🔴" else "🎤", 
                        fontSize = 24.sp,
                        color = if (isMicActive) Color.Red else Color.White
                    )
                }
            }
        )

        // زر التحويل المركزي
        Button(
            onClick = { /* تنفيذ الترجمة */ },
            modifier = Modifier.padding(vertical = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
        ) {
            Text("English ↔ Arabic", color = Color.Black)
        }

        // حقل النتيجة
        OutlinedTextField(
            value = translatedText,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(1.dp, Color(0xFFFF7043), RoundedCornerShape(15.dp)),
            label = { Text("الترجمة المستلمة", color = Color.Gray) },
            readOnly = true,
            textStyle = TextStyle(color = Color(0xFFFFD700), fontSize = 18.sp),
            trailingIcon = {
                IconButton(onClick = { 
                    isSpeakerActive = !isSpeakerActive
                    if (isSpeakerActive) isMicActive = false // منع التداخل
                }) {
                    Text(
                        text = if (isSpeakerActive) "🛑" else "🔊", 
                        fontSize = 24.sp,
                        color = if (isSpeakerActive) Color.Green else Color.White
                    )
                }
            }
        )
    }
}
