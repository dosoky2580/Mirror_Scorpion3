package com.tetocollctionway.mirror.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TranslatorScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // المربع الأول (الإدخال)
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth().weight(1f),
            label = { Text("Enter Text", color = Color.White) },
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.Gray)
        )

        // سطر اختيار اللغة
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { /* تغيير اللغة */ }) {
                Text("English ↔ Arabic")
            }
        }

        // المربع الثاني (الترجمة)
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth().weight(1f),
            label = { Text("Translation", color = Color(0xFFFFD700)) },
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color(0xFFFFD700))
        )
    }
}
