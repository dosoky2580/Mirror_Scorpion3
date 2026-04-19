package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputText = findViewById<EditText>(R.id.inputText)
        val outputText = findViewById<TextView>(R.id.outputText)
        val btnTranslate = findViewById<Button>(R.id.btnTranslate)
        val sourceSpinner = findViewById<Spinner>(R.id.sourceLanguageSpinner)
        val targetSpinner = findViewById<Spinner>(R.id.targetLanguageSpinner)

        // قائمة ببعض اللغات الأساسية كمرحلة أولى (سيتم توسيعها لـ 100)
        val languages = listOf("Arabic", "Turkish", "English", "German", "French", "Russian")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages)
        sourceSpinner.adapter = adapter
        targetSpinner.adapter = adapter

        btnTranslate.setOnClickListener {
            val text = inputText.text.toString()
            if (text.isEmpty()) return@setOnClickListener

            outputText.text = "جاري الترجمة..."

            // إعداد خيارات الترجمة (مثال: من العربية للتركية)
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ARABIC)
                .setTargetLanguage(TranslateLanguage.TURKISH)
                .build()

            val translator = Translation.getClient(options)
            
            // تحميل حزمة اللغة أوفلاين إذا لم تكن موجودة
            translator.downloadModelIfNeeded()
                .addOnSuccessListener {
                    translator.translate(text)
                        .addOnSuccessListener { translatedText ->
                            outputText.text = translatedText
                        }
                        .addOnFailureListener { e ->
                            outputText.text = "خطأ: ${e.message}"
                        }
                }
                .addOnFailureListener { e ->
                    outputText.text = "فشل تحميل اللغة: ${e.message}"
                }
        }
    }
}
