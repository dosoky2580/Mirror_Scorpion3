package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private var isTtsReady = false
    private val REQ_CODE_SPEECH = 100

    private val languageMap = mapOf(
        "العربية" to TranslateLanguage.ARABIC,
        "التركية" to TranslateLanguage.TURKISH,
        "الإنجليزية" to TranslateLanguage.ENGLISH,
        "الفرنسية" to TranslateLanguage.FRENCH,
        "الألمانية" to TranslateLanguage.GERMAN,
        "الإسبانية" to TranslateLanguage.SPANISH
        // ... الـ 100 لغة كاملة في الـ Spinner
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)

        val outputText = findViewById<TextView>(R.id.outputText)
        val micUser = findViewById<ImageButton>(R.id.micUser)
        val micOther = findViewById<ImageButton>(R.id.micOther)
        val sourceSpinner = findViewById<Spinner>(R.id.sourceLanguageSpinner)
        val targetSpinner = findViewById<Spinner>(R.id.targetLanguageSpinner)

        // تفعيل ميكروفون المستخدم (اللغة الأولى)
        micUser.setOnClickListener {
            startVoiceInput(sourceSpinner.selectedItem.toString())
        }

        // تفعيل ميكروفون الطرف الآخر (اللغة الثانية)
        micOther.setOnClickListener {
            startVoiceInput(targetSpinner.selectedItem.toString())
        }
    }

    private fun startVoiceInput(langName: String) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar") // سنضبطها ديناميكياً لاحقاً
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH)
        } catch (e: Exception) {
            Toast.makeText(this, "جهازك لا يدعم التعرف على الصوت", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("ar")
            isTtsReady = true
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) { tts.stop(); tts.shutdown() }
        super.onDestroy()
    }
}
