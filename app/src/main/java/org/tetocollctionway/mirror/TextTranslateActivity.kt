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

        val etSource = findViewById<EditText>(R.id.etSource)
        val tvTarget = findViewById<TextView>(R.id.tvTarget)
        val btnMic = findViewById<ImageButton>(R.id.btnMic)
        
        // محرك الترجمة الأساسي
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.ARABIC)
            .build()
        val translator = Translation.getClient(options)

        // تنفيذ الترجمة التلقائية عند الكتابة أو الضغط (سنطورها لاحقاً للمايك)
        etSource.setOnEditorActionListener { _, _, _ ->
            val text = etSource.text.toString()
            if (text.isNotEmpty()) {
                translator.downloadModelIfNeeded().addOnSuccessListener {
                    translator.translate(text).addOnSuccessListener { result ->
                        tvTarget.text = result
                    }
                }
            }
            true
        }
    }
}
