package com.tetocollctionway.mirror

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.tetocollctionway.mirror.ui.theme.MirrorScorpionTheme
import com.tetocollctionway.mirror.ui.cards.TranslatorScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MirrorScorpionTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main_menu") {
                    composable("main_menu") { MainMenuScreen(navController) }
                    composable("translator") { TranslatorScreen(navController) }
                }
            }
        }
    }
}

@Composable
fun MainMenuScreen(navController: NavController) {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFF1A1A2E), Color(0xFF16213E), Color(0xFF0F3460))
    )

    Column(
        modifier = Modifier.fillMaxSize().background(gradientBackground).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "🦂", fontSize = 40.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "MIRROR PRO", color = Color(0xFFFFD700), fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(30.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            val cards = listOf(
                Triple(R.string.card_translator, "🌐", Color(0xFF42A5F5)),
                Triple(R.string.card_conversation, "💬", Color(0xFFFF7043)),
                Triple(R.string.card_lens, "📷", Color(0xFFFFCA28)),
                Triple(R.string.card_spiritual, "📖", Color(0xFFAB47BC)),
                Triple(R.string.card_games, "🎮", Color(0xFF66BB6A)),
                Triple(R.string.card_settings, "⚙️", Color(0xFF78909C))
            )

            items(cards) { (nameRes, icon, borderColor) ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.07f),
                    modifier = Modifier
                        .height(140.dp)
                        .clickable { if(nameRes == R.string.card_translator) navController.navigate("translator") }
                        .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(20.dp))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = icon, fontSize = 45.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(id = nameRes),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
