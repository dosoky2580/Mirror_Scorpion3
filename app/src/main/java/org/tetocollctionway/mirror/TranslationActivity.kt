package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TranslationActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private lateinit var btnSpeak: ImageButton
    private lateinit var outputEditor: TextView
    private val familyVoices = mapOf(
        "أدهم" to Pair(0.8f, 0.9f),   // صوت رجالي عميق
        "سيف" to Pair(1.2f, 1.1f),    // صوت طفل
        "سلمى" to Pair(1.1f, 1.0f),   // صوت أنثوي رقيق
        "سما" to Pair(1.3f, 1.0f),    // صوت طفلة
        "سارة" to Pair(1.0f, 1.0f)    // صوت طبيعي
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation)

        tts = TextToSpeech(this, this)
        outputEditor = findViewById(R.id.outputEditor)
        btnSpeak = findViewById(R.id.btnSpeak)
        val spinnerVoice = findViewById<Spinner>(R.id.spinnerVoice)

        // إعداد قائمة الأصوات
        val voiceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, familyVoices.keys.toList())
        spinnerVoice.adapter = voiceAdapter

        findViewById<ImageButton>(R.id.btnMic).setOnClickListener {
            if (tts?.isSpeaking == true) tts?.stop()
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            startActivityForResult(intent, 100)
        }

        btnSpeak.setOnClickListener {
            val selectedVoice = spinnerVoice.selectedItem.toString()
            val config = familyVoices[selectedVoice] ?: Pair(1.0f, 1.0f)
            
            tts?.setPitch(config.first)
            tts?.setSpeechRate(config.second)
            
            val text = outputEditor.text.toString()
            if (text.isNotEmpty() && text != "الترجمة ستظهر هنا...") {
                tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) tts?.language = Locale("tr") // لغة المصنع الافتراضية
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            findViewById<EditText>(R.id.inputEditor).setText(result?.get(0))
            // هنا ستظهر الترجمة (سنفعل المحرك في الخطوة القادمة)
            btnSpeak.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        tts?.shutdown()
        super.onDestroy()
    }
}
