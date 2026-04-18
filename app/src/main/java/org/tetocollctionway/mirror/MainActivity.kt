package org.tetocollctionway.mirror

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ScorpionMainScreen()
            }
        }
    }
}

@Composable
fun ScorpionMainScreen() {
    var selectedLanguage by remember { mutableStateOf("العربية (Ar)") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- الجزء العلوي: العقرب والانعكاس (مساحة 25% من الشاشة) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // ارتفاع تقريبي لمكان الشعار
                .background(Color(0xFFF8F8F8), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Scorpion Logo", color = Color.Gray, fontWeight = FontWeight.Bold)
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp).width(100.dp),
                    color = Color.LightGray
                )
                Text("Reflection Mirror", color = Color(0xFFAAAAAA), fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- زرار تحديد اللغة (في منتصف الشاشة العلوي) ---
        Button(
            onClick = { /* سيتم ربطه بقائمة الـ 100 لغة */ },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A1A)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = selectedLanguage, color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- شبكة الـ 6 كروت ---
        val cards = listOf(
            "ترجمة نصية", "حوار مترجم", "ترجمة مستندات وعدسة",
            "أحاديث وقصص وإلهام", "الألعاب (شطرنج 3D)", "الإعدادات"
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(cards.size) { index ->
                ScorpionCard(title = cards[index])
            }
        }
        
        // تذييل بسيط يحمل هويتك
        Text(
            text = "TetoCollctionWay",
            fontSize = 12.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScorpionCard(title: String) {
    Card(
        onClick = { /* التنقل للشاشات الفرعية */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF2C2C2C), Color(0xFF000000))
                    )
                )
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }
    }
}
