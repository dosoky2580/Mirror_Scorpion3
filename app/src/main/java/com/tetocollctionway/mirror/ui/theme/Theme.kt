package com.tetocollctionway.mirror.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ScorpionColorScheme = darkColorScheme(
    primary = ScorpionGold,
    secondary = ScorpionRoyalBlue,
    background = ScorpionBackground,
    surface = ScorpionSurface
)

@Composable
fun MirrorScorpionTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ScorpionColorScheme,
        content = content
    )
}
