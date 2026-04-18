package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.*

class TranslationActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private lateinit var inputEditor: EditText
    private lateinit var outputEditor: TextView
    private lateinit var btnSpeak: ImageButton
    private lateinit var btnMic: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation)
        
        tts = TextToSpeech(this, this)
        inputEditor = findViewById(R.id.inputEditor)
        outputEditor = findViewById(R.id.outputEditor)
        btnMic = findViewById(R.id.btnMic)
        btnSpeak = findViewById(R.id.btnSpeak)
        
        btnSpeak.visibility = View.GONE

        // إعداد محرك الترجمة (عربي إلى تركي كمثال مبدئي)
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ARABIC)
            .setTargetLanguage(TranslateLanguage.TURKISH)
            .build()
        val translator = Translation.getClient(options)

        // تحميل القاموس في الخلفية
        val conditions = DownloadConditions.Builder().requireWifi().build()
        translator.downloadModelIfNeeded(conditions)

        btnMic.setOnClickListener {
            if (tts?.isSpeaking == true) tts?.stop()
            startSpeechToText()
        }

        // ترجمة فورية أثناء الكتابة
        inputEditor.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()
                if (text.isNotEmpty()) {
                    translator.translate(text)
                        .addOnSuccessListener { translated ->
                            outputEditor.text = translated
                            btnSpeak.visibility = View.VISIBLE
                        }
                } else {
                    outputEditor.text = ""
                    btnSpeak.visibility = View.GONE
                }
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        btnSpeak.setOnClickListener {
            tts?.speak(outputEditor.text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    private fun startSpeechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        try {
            startActivityForResult(intent, 100)
        } catch (e: Exception) {
            Toast.makeText(this, "الميكروفون غير مدعوم حالياً", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            inputEditor.setText(result?.get(0))
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) tts?.language = Locale("tr")
    }

    override fun onDestroy() {
        tts?.shutdown()
        super.onDestroy()
    }
}
