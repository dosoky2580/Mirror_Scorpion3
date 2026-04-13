package com.tetocollctionway.mirror.ui.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tetocollctionway.mirror.ui.theme.ScorpionGold

@Composable
fun StoriesLibraryCard(storiesJson: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("مكتبة القصص المختارة 📖", color = ScorpionGold, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            
            // عرض عينة من القصص المجانية
            Text("القصص المتاحة (25 قصة مجانية):", color = Color.LightGray, style = MaterialTheme.typography.labelMedium)
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // شريط عرض سريع للقصص
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(listOf("صبر أيوب", "يوسف والصدق", "آسية بنت مزاحم", "نملة سليمان", "أصحاب الأخدود")) { title ->
                    SuggestionChip(
                        onClick = { /* فتح القصة */ },
                        label = { Text(title, color = Color.White) },
                        colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color(0xFF333333))
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Button(
                onClick = { /* الانتقال لصفحة المكتبة الكاملة */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = ScorpionGold)
            ) {
                Text("تصفح جميع التصنيفات", color = Color.Black)
            }
        }
    }
}
