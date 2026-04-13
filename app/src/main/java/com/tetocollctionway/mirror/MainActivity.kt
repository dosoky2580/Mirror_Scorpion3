package com.tetocollctionway.mirror

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tetocollctionway.mirror.ui.theme.MirrorScorpionTheme
import com.tetocollctionway.mirror.ui.theme.ScorpionGold
import com.tetocollctionway.mirror.ui.cards.TranslatorCard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MirrorScorpionTheme {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        // العقرب وانعكاسه (بصورة مذهلة)
                        Column(
                            modifier = Modifier.padding(top = 40.dp, bottom = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🦂", fontSize = 80.sp) // سيتم استبدالها بصورة حقيقية لاحقاً
                            Text(
                                "🦂", 
                                fontSize = 80.sp,
                                modifier = Modifier
                                    .graphicsLayer(scaleY = -0.6f, alpha = 0.3f) // الانعكاس
                            )
                        }
                    }

                    item { TranslatorCard() }
                    
                    // بقية الكروت الـ 5 ستضاف هنا تباعاً
                    item { Text("بقية الكروت قيد الإنشاء...", color = ScorpionGold, modifier = Modifier.padding(20.dp)) }
                }
            }
        }
    }
}
