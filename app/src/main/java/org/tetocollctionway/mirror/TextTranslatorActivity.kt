package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.*

class TextTranslatorActivity : AppCompatActivity() {

    private lateinit var etInput: EditText
    private lateinit var tvOutput: TextView
    private var selectedLang = TranslateLanguage.TURKISH // اللغة الافتراضية التركية كما تحب

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translator)

        etInput = findViewById(R.id.et_input_text)
        tvOutput = findViewById(R.id.tv_translated_text)
        val btnMic = findViewById<ImageButton>(R.id.btn_mic)

        btnMic.setOnClickListener { startSpeech() }
    }

    private fun startSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-SA") // تحديد العربي كمصدر
        try {
            startActivityForResult(intent, 100)
        } catch (e: Exception) {
            Toast.makeText(this, "جوجل فويس غير متاح", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val recognizedText = result?.get(0) ?: ""
            
            // 1. كتابة النص فوراً في الخانة العليا
            etInput.setText(recognizedText)
            
            // 2. البدء في الترجمة فوراً تلقائياً
            translateAutomatically(recognizedText)
        }
    }

    private fun translateAutomatically(text: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ARABIC)
            .setTargetLanguage(selectedLang)
            .build()
        val translator = Translation.getClient(options)
        
        tvOutput.text = "جاري الترجمة..."
        
        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                translator.translate(text)
                    .addOnSuccessListener { translatedText ->
                        // 3. عرض النتيجة في الخانة السفلى
                        tvOutput.text = translatedText
                    }
                    .addOnFailureListener {
                        tvOutput.text = "فشلت الترجمة، حاول ثانية."
                    }
            }
            .addOnFailureListener {
                tvOutput.text = "برجاء الاتصال بالإنترنت لتحميل قاموس اللغة."
            }
    }
}
