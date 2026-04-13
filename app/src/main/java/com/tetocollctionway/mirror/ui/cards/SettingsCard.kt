package com.tetocollctionway.mirror.ui.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tetocollctionway.mirror.ui.theme.ScorpionGold

@Composable
fun SettingsCard(isPremium: Boolean = false) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF252525))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("الإعدادات والترقية ⚙️", color = ScorpionGold, fontWeight = FontWeight.Bold)
            
            Spacer(modifier = Modifier.height(16.dp))

            // ميزة تحميل القصص Word
            Button(
                onClick = { if (isPremium) { /* تصدير JSON لـ Word */ } },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPremium) ScorpionGold else Color.Gray
                )
            ) {
                Text(
                    text = if (isPremium) "تحميل مكتبة القصص (Word) 📄" else "تحميل المكتبة كاملة (PRO فقط) 🔒",
                    color = Color.White
                )
            }

            if (!isPremium) {
                Text(
                    "النسخة العادية تتيح لك 25 قصة فقط (أنبياء، نساء، حيوان...)",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ميزة الإلهام الذكي
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("رسائل الإلهام الذكي ✨", color = Color.White)
                Switch(
                    checked = isPremium, 
                    onCheckedChange = { /* تفعيل فقط في المدفوع */ },
                    enabled = isPremium 
                )
            }
            if (!isPremium) {
                Text("فهم الحالة النفسية متاح في النسخة المدفوعة", color = ScorpionGold.copy(alpha = 0.6f), style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
