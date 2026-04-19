package org.tetocollctionway.mirror

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.nl.translate.*
import java.util.*

class ChatTranslateActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tvSource: TextView
    private lateinit var tvTarget: TextView
    private var tts: TextToSpeech? = null
    private val REQ_SOURCE = 101
    private val REQ_TARGET = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_translate)

        tvSource = findViewById(R.id.tvSourceChat)
        tvTarget = findViewById(R.id.tvTargetChat)
        val btnSource = findViewById<ImageButton>(R.id.btnMicSource)
        val btnTarget = findViewById<ImageButton>(R.id.btnMicTarget)

        tts = TextToSpeech(this, this)

        btnSource.setOnClickListener { startMic(REQ_SOURCE, "ar-SA") }
        btnTarget.setOnClickListener { startMic(REQ_TARGET, "tr-TR") }
    }

    private fun startMic(reqCode: Int, lang: String) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang)
        }
        startActivityForResult(intent, reqCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
            if (requestCode == REQ_SOURCE) translateAndSpeak(text, TranslateLanguage.ARABIC, TranslateLanguage.TURKISH, true)
            else translateAndSpeak(text, TranslateLanguage.TURKISH, TranslateLanguage.ARABIC, false)
        }
    }

    private fun translateAndSpeak(text: String, from: String, to: String, isToTarget: Boolean) {
        val options = TranslatorOptions.Builder().setSourceLanguage(from).setTargetLanguage(to).build()
        val translator = Translation.getClient(options)
        
        if (isToTarget) tvSource.text = text else tvTarget.text = text

        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(text).addOnSuccessListener { result ->
                if (isToTarget) {
                    tvTarget.text = result
                    tts?.language = Locale("tr")
                } else {
                    tvSource.text = result
                    tts?.language = Locale("ar")
                }
                tts?.speak(result, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    override fun onInit(status: Int) {}
    override fun onDestroy() { tts?.stop(); tts?.shutdown(); super.onDestroy() }
}
