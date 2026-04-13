package com.tetocollctionway.mirror.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.tetocollctionway.mirror.ui.theme.ScorpionGold
import com.tetocollctionway.mirror.ui.theme.ScorpionRoyalBlue

@Composable
fun ConversationCard() {
    var upperConversation by remember { mutableStateOf("") }
    var lowerConversation by remember { mutableStateOf("") }
    var sourceLang by remember { mutableStateOf("العربية") }
    var targetLang by remember { mutableStateOf("الإنجليزية") }

    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = ScorpionRoyalBlue.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("حوار مترجم", color = ScorpionGold, fontWeight = FontWeight.Bold)
            
            Spacer(modifier = Modifier.height(10.dp))

            // 1. المربع العلوي (يستخدم دائماً لغة الزر الأيمن)
            OutlinedTextField(
                value = upperConversation,
                onValueChange = { upperConversation = it },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                placeholder = { Text("النص الأصلي...", color = Color.Gray) },
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = ScorpionGold.copy(0.3f))
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 2. المربع السفلي (للترجمة مع سبيكر بنطق احترافي)
            Box(modifier = Modifier.fillMaxWidth().height(120.dp)) {
                OutlinedTextField(
                    value = lowerConversation,
                    onValueChange = { lowerConversation = it },
                    modifier = Modifier.fillMaxSize(),
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = ScorpionGold)
                )
                IconButton(
                    onClick = { /* نطق الترجمة */ },
                    modifier = Modifier.align(Alignment.BottomEnd).padding(4.dp)
                ) { Text("🔊") }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 3. شريط التحكم السفلي (اللغات، التبديل، والمايك الكبير)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // زر اللغة اليمين (المتحكم في المحرر العلوي)
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(ScorpionGold)) {
                    Text(sourceLang, color = Color.Black, fontSize = 12.sp)
                }

                // زر التبديل
                IconButton(onClick = { 
                    val temp = sourceLang
                    sourceLang = targetLang
                    targetLang = temp
                }) { Text("🔄", fontSize = 20.sp) }

                // المايك الكبير (قلب الكرت)
                Surface(
                    modifier = Modifier.size(65.dp),
                    shape = CircleShape,
                    color = ScorpionGold,
                    onClick = { /* مسح الشاشة وبدء التقاط جديد */ 
                        upperConversation = ""
                        lowerConversation = ""
                    }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("🎤", fontSize = 30.sp)
                    }
                }

                // زر اللغة اليسار
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(ScorpionGold)) {
                    Text(targetLang, color = Color.Black, fontSize = 12.sp)
                }
            }
        }
    }
}
