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
    private val REQ_CODE_USER = 100
    private val REQ_CODE_OTHER = 101

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

        btnTranslate.setOnClickListener { 
            processTranslation(inputText.text.toString(), getLangCode(sourceSpinner), getLangCode(targetSpinner), outputText) 
        }

        btnSpeak.setOnClickListener { speak(outputText.text.toString(), voiceSpinner.selectedItemPosition) }
        
        // ميكروفونك (يسمع اللغة الأولى ويترجم للثانية)
        micUser.setOnClickListener { startVoiceInput(getLangLocale(sourceSpinner), REQ_CODE_USER) }
        
        // ميكروفون الطرف الآخر (يسمع اللغة الثانية ويترجم للأولى)
        micOther.setOnClickListener { startVoiceInput(getLangLocale(targetSpinner), REQ_CODE_OTHER) }
    }

    private fun getLangCode(spinner: Spinner): String = languageMap[spinner.selectedItem.toString()] ?: TranslateLanguage.ENGLISH
    
    private fun getLangLocale(spinner: Spinner): String {
        return when(spinner.selectedItem.toString()) {
            "العربية" -> "ar"
            "التركية" -> "tr"
            "الإنجليزية" -> "en"
            "الفرنسية" -> "fr"
            "الألمانية" -> "de"
            else -> "en"
        }
    }

    private fun startVoiceInput(locale: String, requestCode: Int) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale)
        try { startActivityForResult(intent, requestCode) } catch (e: Exception) {}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = result?.get(0) ?: ""
            val outputText = findViewById<TextView>(R.id.outputText)
            val sourceSpinner = findViewById<Spinner>(R.id.sourceLanguageSpinner)
            val targetSpinner = findViewById<Spinner>(R.id.targetLanguageSpinner)
            val voiceSpinner = findViewById<Spinner>(R.id.voiceSpinner)

            if (requestCode == REQ_CODE_USER) {
                processTranslation(spokenText, getLangCode(sourceSpinner), getLangCode(targetSpinner), outputText, true, voiceSpinner.selectedItemPosition)
            } else if (requestCode == REQ_CODE_OTHER) {
                processTranslation(spokenText, getLangCode(targetSpinner), getLangCode(sourceSpinner), outputText, true, voiceSpinner.selectedItemPosition)
            }
        }
    }

    private fun processTranslation(text: String, source: String, target: String, output: TextView, autoSpeak: Boolean = false, voicePos: Int = 0) {
        if (text.isEmpty()) return
        val options = TranslatorOptions.Builder().setSourceLanguage(source).setTargetLanguage(target).build()
        val translator = Translation.getClient(options)
        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(text).addOnSuccessListener { translated ->
                output.text = translated
                if (autoSpeak) speak(translated, voicePos)
            }
        }
    }

    private fun speak(text: String, voicePos: Int) {
        if (isTtsReady && text.isNotEmpty()) {
            if (voicePos == 0) tts.setPitch(0.8f) else tts.setPitch(1.1f)
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    override fun onInit(status: Int) { if (status == TextToSpeech.SUCCESS) isTtsReady = true }
}
