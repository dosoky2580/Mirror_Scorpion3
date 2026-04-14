package com.tetocollctionway.mirror

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tetocollctionway.mirror.ui.theme.MirrorScorpionTheme
import com.tetocollctionway.mirror.ui.cards.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MirrorScorpionTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black) // خلفية سوداء فخمة
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 1. العقرب (اللوجو) فوق وصغير
                    Column(
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🦂", fontSize = 40.sp) // صغرنا الحجم
                        Text("🦂", fontSize = 40.sp, 
                            modifier = Modifier.graphicsLayer(scaleY = -0.5f, alpha = 0.2f))
                    }

                    // 2. قسم الترجمة (المربعين) - بياخد مساحة أكبر
                    Box(modifier = Modifier.weight(1.5f)) {
                        TranslatorCard() 
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 3. الـ 6 كروت في واجهة رئيسية (Grid 2 columns)
                    Box(modifier = Modifier.weight(1f)) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2), // تقسيم الشاشة عمودين
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            item { ConversationCard() }
                            item { DocumentLensCard() }
                            item { SpiritualInspirationCard() }
                            item { GamesHubCard() }
                            item { SettingsCard(isPremium = false) }
                            // ممكن تكرر كارت أو تضيف كارت جديد هنا للرقم 6
                        }
                    }
                }
            }
        }
    }
}
