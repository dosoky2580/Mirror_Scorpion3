package com.tetocollctionway.mirror.ui.cards

import androidx.compose.foundation.layout.*
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
fun SpiritualInspirationCard() {
    var inspirationEnabled by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = ScorpionRoyalBlue.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("أحاديث وقصص وإلهام", color = ScorpionGold, fontWeight = FontWeight.Bold)
            
            Spacer(modifier = Modifier.height(16.dp))

            // 1. زر الأحاديث القدسية
            Button(
                onClick = { /* عرض حديث عشوائي من قاعدة البيانات */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = ScorpionGold),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("أحاديث قدسية 📖", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 2. زر الإلهام (الذكاء الاصطناعي)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = { /* رسالة ملهمة بناءً على حالة المستخدم */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                ) {
                    Text("إلهام (AI) ✨", color = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = inspirationEnabled,
                    onCheckedChange = { inspirationEnabled = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = ScorpionGold)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 3. زر قصص القرآن (الروح)
            Button(
                onClick = { /* الدخول لقسم القصص (أنبياء، نساء، حيوان...) */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = ScorpionRoyalBlue)
            ) {
                Text("قصص القرآن 🌙", color = Color.White)
            }
        }
    }
}
