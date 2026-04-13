package com.tetocollctionway.mirror.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tetocollctionway.mirror.ui.theme.ScorpionGold
import com.tetocollctionway.mirror.ui.theme.ScorpionRoyalBlue

@Composable
fun TranslatorCard() {
    var upperText by remember { mutableStateOf("") }
    var lowerText by remember { mutableStateOf("") }
    var selectedLanguage by remember { mutableStateOf("الإنجليزية") }

    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = ScorpionRoyalBlue.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            
            // 1. زر اختيار اللغة (منتصف الشاشة العلوي داخل الكرت)
            Button(
                onClick = { /* قائمة 100 لغة */ },
                colors = ButtonDefaults.buttonColors(containerColor = ScorpionGold),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(selectedLanguage, color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. المحرر العلوي (Input)
            Box(modifier = Modifier.fillMaxWidth().height(150.dp)) {
                OutlinedTextField(
                    value = upperText,
                    onValueChange = { upperText = it },
                    modifier = Modifier.fillMaxSize(),
                    placeholder = { Text("اكتب أو تحدث للترجمة...", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ScorpionGold,
                        unfocusedBorderColor = ScorpionGold.copy(alpha = 0.3f)
                    )
                )
                // مايك في أسفل اليسار
                IconButton(
                    onClick = { /* التقاط الكلام وتعديله بالكيبورد */ },
                    modifier = Modifier.align(Alignment.BottomStart).padding(4.dp)
                ) {
                    Text("🎤", fontSize = 20.sp) 
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. المحرر السفلي (Output)
            Box(modifier = Modifier.fillMaxWidth().height(150.dp)) {
                OutlinedTextField(
                    value = lowerText,
                    onValueChange = { lowerText = it },
                    modifier = Modifier.fillMaxSize(),
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ScorpionGold
                    )
                )
                
                // سبيكر، مشاركة، ونسخ في أسفل اليمين
                Row(modifier = Modifier.align(Alignment.BottomEnd).padding(4.dp)) {
                    IconButton(onClick = { /* نطق + ختم ميرور سكربيون */ }) { Text("🔊") }
                    IconButton(onClick = { /* مشاركة ملف الصوت فقط */ }) { Text("🔗") }
                    IconButton(onClick = { /* نسخ النص */ }) { Text("📋") }
                }
            }
        }
    }
}
