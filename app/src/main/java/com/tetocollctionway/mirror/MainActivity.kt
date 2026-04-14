package com.tetocollctionway.mirror

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tetocollctionway.mirror.ui.theme.MirrorScorpionTheme
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
                    // هنضيف باقي الكروت هنا لما نجهز صفحاتها
                }
            }
        }
    }
}
