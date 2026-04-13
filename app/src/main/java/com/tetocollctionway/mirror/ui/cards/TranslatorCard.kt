package com.tetocollctionway.mirror.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tetocollctionway.mirror.ui.theme.ScorpionGold
import com.tetocollctionway.mirror.ui.theme.ScorpionRoyalBlue

@Composable
fun TranslatorCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = ScorpionRoyalBlue.copy(alpha = 0.15f)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "الترجمة الفورية (Scorpion AI)",
                color = ScorpionGold,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            // حقل النص المحدث (الختم الذهبي المائل)
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("اكتب أو تحدث للترجمة...", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ScorpionGold,
                    unfocusedBorderColor = ScorpionGold.copy(alpha = 0.5f)
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(
                    onClick = { /* الترجمة */ },
                    colors = ButtonDefaults.buttonColors(containerColor = ScorpionGold),
                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                ) {
                    Text("ترجمة", color = Color.Black, fontWeight = FontWeight.Bold)
                }
                
                IconButton(
                    onClick = { /* تفعيل المايك */ },
                    modifier = Modifier.background(ScorpionGold, RoundedCornerShape(50.dp))
                ) {
                    // هنا سيتم إضافة أيقونة المايك لاحقاً
                }
            }
        }
    }
}
