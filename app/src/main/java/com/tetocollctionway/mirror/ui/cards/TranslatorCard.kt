package com.tetocollctionway.mirror.ui.cards

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun TranslatorCard(onClick: () -> Unit = {}) {
    // كارت بسيط يفتح صفحة الترجمة لما تدوس عليه
    Text(
        text = "🌐 Translation", 
        fontSize = 20.sp, 
        modifier = Modifier.clickable { onClick() }
    )
}
