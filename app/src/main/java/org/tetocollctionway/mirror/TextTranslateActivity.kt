package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.nl.translate.TranslateLanguage

class TextTranslateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translate)

        val etSource = findViewById<EditText>(R.id.etSourceText)
        val btnTranslate = findViewById<Button>(R.id.btnTranslateNow)
        val tvResult = findViewById<TextView>(R.id.tvTranslatedResult)

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.ARABIC)
            .build()
        val translator = Translation.getClient(options)

        btnTranslate.setOnClickListener {
            val text = etSource.text.toString()
            if (text.isNotEmpty()) {
                tvResult.text = "جاري الترجمة..."
                translator.downloadModelIfNeeded()
                    .addOnSuccessListener {
                        translator.translate(text)
                            .addOnSuccessListener { translatedText ->
                                tvResult.text = translatedText
                            }
                            .addOnFailureListener {
                                tvResult.text = "فشلت الترجمة"
                            }
                    }
                    .addOnFailureListener {
                        tvResult.text = "يجب تحميل حزمة اللغة أولاً"
                    }
            }
        }
    }
}
