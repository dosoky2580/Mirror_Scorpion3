package org.tetocollctionway.mirror

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private var isTtsReady = false
    private val voiceList = listOf("سيف (رجل)", "سلمى (بنت)", "سما (بنت)", "سارة (بنت)", "صوتك (نسخة مدفوعة)")

    private val languageMap = mapOf(
        "العربية" to TranslateLanguage.ARABIC,
        "التركية" to TranslateLanguage.TURKISH,
        "الإنجليزية" to TranslateLanguage.ENGLISH
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)

        val inputText = findViewById<EditText>(R.id.inputText)
        val outputText = findViewById<TextView>(R.id.outputText)
        val btnTranslate = findViewById<Button>(R.id.btnTranslate)
        val btnSpeak = findViewById<ImageButton>(R.id.btnSpeak)
        val voiceSpinner = findViewById<Spinner>(R.id.voiceSpinner)

        val voiceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, voiceList)
        voiceSpinner.adapter = voiceAdapter

        btnTranslate.setOnClickListener {
            val text = inputText.text.toString()
            if (text.isEmpty()) return@setOnClickListener
            
            // منطق الترجمة (كما هو في الأساس المستقر)
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ARABIC)
                .setTargetLanguage(TranslateLanguage.TURKISH)
                .build()
            val translator = Translation.getClient(options)
            translator.downloadModelIfNeeded().addOnSuccessListener {
                translator.translate(text).addOnSuccessListener { outputText.text = it }
            }
        }

        btnSpeak.setOnClickListener {
            val selectedVoice = voiceSpinner.selectedItemPosition
            if (selectedVoice == 4) {
                Toast.makeText(this, "هذه الميزة متاحة في النسخة المدفوعة فقط", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // تخصيص نبرة الصوت (سيف = ذكر، الباقي = إناث)
            if (selectedVoice == 0) {
                tts.setPitch(0.8f) // نبرة غليظة لسيف
            } else {
                tts.setPitch(1.2f) // نبرة حادة للبنات
            }
            
            val text = outputText.text.toString()
            if (isTtsReady && text.isNotEmpty()) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("ar") // تعيين اللغة العربية كافتراضي للنطق
            isTtsReady = true
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) { tts.stop(); tts.shutdown() }
        super.onDestroy()
    }
}
