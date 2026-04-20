package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.EditText
import android.widget.TextView
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.Locale

class TextTranslatorActivity : AppCompatActivity() {

    private lateinit var etInput: EditText
    private lateinit var tvOutput: TextView
    private var selectedLang = TranslateLanguage.TURKISH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translator)

        // الربط اليدوي لضمان عدم وجود Unresolved reference
        etInput = findViewById<EditText>(R.id.et_input_text)
        tvOutput = findViewById<TextView>(R.id.tv_translated_text)
        val btnMic = findViewById<ImageButton>(R.id.btn_mic)

        btnMic.setOnClickListener { startSpeech() }
    }

    private fun startSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        try {
            startActivityForResult(intent, 100)
        } catch (e: Exception) {
            Toast.makeText(this, "Speech recognition error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val recognizedText = result?.get(0) ?: ""
            etInput.setText(recognizedText)
            translateNow(recognizedText)
        }
    }

    private fun translateNow(text: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ARABIC)
            .setTargetLanguage(selectedLang)
            .build()
        val translator = Translation.getClient(options)
        
        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(text).addOnSuccessListener { translatedText ->
                tvOutput.text = translatedText
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Translation failed", Toast.LENGTH_SHORT).show()
        }
    }
}
