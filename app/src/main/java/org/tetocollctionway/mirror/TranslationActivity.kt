package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TranslationActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private lateinit var btnSpeak: ImageButton
    private val voicesList = listOf("أدهم", "سيف", "سلمى", "سما", "سارة")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation)

        tts = TextToSpeech(this, this)
        val spinnerVoice = findViewById<Spinner>(R.id.spinnerVoice)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, voicesList)
        spinnerVoice.adapter = adapter

        btnSpeak = findViewById(R.id.btnSpeak)
        val btnMic = findViewById<ImageButton>(R.id.btnMic)
        val outputEditor = findViewById<TextView>(R.id.outputEditor)

        btnMic.setOnClickListener {
            if (tts?.isSpeaking == true) tts?.stop()
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            startActivityForResult(intent, 100)
        }

        btnSpeak.setOnClickListener {
            val text = outputEditor.text.toString()
            if (text.isNotEmpty() && text != "الترجمة ستظهر هنا...") {
                // منطق اختيار الصوت بناءً على القائمة
                tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.getDefault()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val res = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            findViewById<EditText>(R.id.inputEditor).setText(res?.get(0))
            // سيتم استدعاء محرك الترجمة هنا ليظهر السبيكر
            btnSpeak.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        tts?.shutdown()
        super.onDestroy()
    }
}
