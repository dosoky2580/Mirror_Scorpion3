package org.tetocollctionway.mirror

import android.os.Bundle
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

    private val languageMap = mapOf(
        "العربية" to TranslateLanguage.ARABIC,
        "التركية" to TranslateLanguage.TURKISH,
        "الإنجليزية" to TranslateLanguage.ENGLISH,
        "الألمانية" to TranslateLanguage.GERMAN,
        "الفرنسية" to TranslateLanguage.FRENCH,
        "الإيطالية" to TranslateLanguage.ITALIAN
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)

        val inputText = findViewById<EditText>(R.id.inputText)
        val outputText = findViewById<TextView>(R.id.outputText)
        val btnTranslate = findViewById<Button>(R.id.btnTranslate)
        val btnSpeak = findViewById<ImageButton>(R.id.btnSpeak)
        val sourceSpinner = findViewById<Spinner>(R.id.sourceLanguageSpinner)
        val targetSpinner = findViewById<Spinner>(R.id.targetLanguageSpinner)

        val langList = languageMap.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, langList)
        sourceSpinner.adapter = adapter
        targetSpinner.adapter = adapter

        btnTranslate.setOnClickListener {
            val text = inputText.text.toString()
            val sourceLang = languageMap[sourceSpinner.selectedItem.toString()] ?: TranslateLanguage.ENGLISH
            val targetLang = languageMap[targetSpinner.selectedItem.toString()] ?: TranslateLanguage.TURKISH

            if (text.isEmpty()) return@setOnClickListener

            val options = TranslatorOptions.Builder()
                .setSourceLanguage(sourceLang)
                .setTargetLanguage(targetLang)
                .build()

            val translator = Translation.getClient(options)
            translator.downloadModelIfNeeded().addOnSuccessListener {
                translator.translate(text).addOnSuccessListener { translatedText ->
                    outputText.text = translatedText
                }
            }
        }

        btnSpeak.setOnClickListener {
            val textToSpeak = outputText.text.toString()
            if (isTtsReady && textToSpeak.isNotEmpty()) {
                tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.getDefault()
            isTtsReady = true
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}
