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
        "الألمانية" to TranslateLanguage.GERMAN
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)

        val inputText = findViewById<EditText>(R.id.inputText)
        val outputText = findViewById<TextView>(R.id.outputText)
        val btnTranslate = findViewById<Button>(R.id.btnTranslate)
        val btnSpeak = findViewById<ImageButton>(R.id.btnSpeak)
        val micUser = findViewById<ImageButton>(R.id.micUser)
        val micOther = findViewById<ImageButton>(R.id.micOther)
        val sourceSpinner = findViewById<Spinner>(R.id.sourceLanguageSpinner)
        val targetSpinner = findViewById<Spinner>(R.id.targetLanguageSpinner)
        val voiceSpinner = findViewById<Spinner>(R.id.voiceSpinner)

        val langList = languageMap.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, langList)
        sourceSpinner.adapter = adapter
        targetSpinner.adapter = adapter

        val voices = listOf("سيف", "سلمى", "سما", "سارة", "صوتك")
        voiceSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, voices)

        btnTranslate.setOnClickListener { translate(inputText.text.toString(), outputText) }
        btnSpeak.setOnClickListener { speak(outputText.text.toString()) }
        
        micUser.setOnClickListener { startVoiceInput() }
        micOther.setOnClickListener { startVoiceInput() }
    }

    private fun translate(text: String, output: TextView) {
        if (text.isEmpty()) return
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ARABIC)
            .setTargetLanguage(TranslateLanguage.TURKISH)
            .build()
        val translator = Translation.getClient(options)
        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(text).addOnSuccessListener { output.text = it }
        }
    }

    private fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        try { startActivityForResult(intent, REQ_CODE_SPEECH) } catch (e: Exception) {}
    }

    private fun speak(text: String) {
        if (isTtsReady && text.isNotEmpty()) tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onInit(status: Int) { if (status == TextToSpeech.SUCCESS) isTtsReady = true }
}
