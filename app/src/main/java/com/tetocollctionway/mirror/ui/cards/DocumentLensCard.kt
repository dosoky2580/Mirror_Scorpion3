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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tetocollctionway.mirror.ui.theme.ScorpionGold
import com.tetocollctionway.mirror.ui.theme.ScorpionRoyalBlue

@Composable
fun DocumentLensCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = ScorpionRoyalBlue.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("ترجمة مستندات وعدسة", color = ScorpionGold, fontWeight = FontWeight.Bold)
            
            Spacer(modifier = Modifier.height(16.dp))

            // زر العدسة (بصمة جوجل)
            Button(
                onClick = { /* فتح الكاميرا - تقنية Lens */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = ScorpionGold),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("دخول إلى العدسة 🔍", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = ScorpionGold.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(20.dp))

            // قسم المستندات
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("رابط المستند...", fontSize = 12.sp) },
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(ScorpionGold)) {
                    Text("بحث", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* فتح مستعرض الجهاز */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text("فتح من المستعرض 📂", color = Color.White)
            }
        }
    }
}
