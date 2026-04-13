package com.tetocollctionway.mirror

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tetocollctionway.mirror.ui.theme.MirrorScorpionTheme
import com.tetocollctionway.mirror.ui.theme.ScorpionGold
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MirrorScorpionTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "MIRROR SCORPION\nUltimate Edition",
                        color = ScorpionGold,
                        fontSize = 32.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 40.sp
                    )
                }
            }
        }
    }
}
