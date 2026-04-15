package com.tetocollctionway.mirror.ui.cards

import android.speech.tts.TextToSpeech
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.*

@Composable
fun TranslatorScreen(navController: NavController) {
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }
    var translatedText by remember { mutableStateOf("") }
    var isTranslated by remember { mutableStateOf(false) }
    
    // محرك النطق (TTS)
    val tts = remember { 
        var textToSpeech: TextToSpeech? = null
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale("ar") // أو حسب اللغة المختارة
            }
        }
        textToSpeech
    }

    // محرك التعرف على الصوت (المايك)
    val speechLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        inputText = data?.get(0) ?: ""
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF0F172A)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // زر الـ 100 لغة
        Button(
            onClick = { /* هنا هنفتح المنيو اللي فيها الـ 100 لغة */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
        ) {
            Text("English ↔ Arabic", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // المحرر العلوي
        Box(modifier = Modifier.fillMaxWidth().weight(1f).background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(15.dp))) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { 
                    if (isTranslated) { inputText = it; translatedText = ""; isTranslated = false }
                    else { inputText = it }
                },
                modifier = Modifier.fillMaxSize(),
                textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color.Transparent, unfocusedBorderColor = Color.Transparent)
            )
            // تفعيل المايك (يسار)
            IconButton(
                onClick = {
                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                    }
                    speechLauncher.launch(intent)
                },
                modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)
            ) {
                Text("🎤", fontSize = 24.sp)
            }
        }

        // زر تنفيذ الترجمة (الموتور)
        Button(
            onClick = {
                // مؤقتاً هنحاكي الترجمة لحد ما نربط الـ API Key
                translatedText = "جاري الاتصال بمحرك الترجمة..." 
                isTranslated = true
            },
            modifier = Modifier.padding(vertical = 10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
        ) {
            Text("ترجم الآن", color = Color.Black)
        }

        // المحرر السفلي
        Box(modifier = Modifier.fillMaxWidth().weight(1f).background(Color.White.copy(alpha = 0.08f), RoundedCornerShape(15.dp))) {
            Text(text = translatedText, modifier = Modifier.fillMaxSize().padding(16.dp), color = Color(0xFFFFD700), fontSize = 18.sp)
            
            Row(modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)) {
                // نسخ
                IconButton(onClick = { /* كود النسخ */ }) { Text("📋") }
                // مشاركة
                IconButton(onClick = { /* كود مشاركة الصوت */ }) { Text("📤") }
                // نطق (سبيكر)
                IconButton(onClick = { tts?.speak(translatedText, TextToSpeech.QUEUE_FLUSH, null, null) }) { 
                    Text("🔊") 
                }
            }
        }
    }
}
