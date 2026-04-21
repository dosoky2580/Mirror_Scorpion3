package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.tetocollctionway.mirror.databinding.ActivityTextTranslatorBinding
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class TextTranslatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTextTranslatorBinding
    private var targetLangCode: String = TranslateLanguage.TURKISH 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextTranslatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val langMap = mapOf(
            "التركية" to TranslateLanguage.TURKISH,
            "الإنجليزية" to TranslateLanguage.ENGLISH,
            "البنغالية" to TranslateLanguage.BENGALI,
            "الهندية" to TranslateLanguage.HINDI,
            "السيرلانكية" to "si"
        )

        binding.btnSelectLanguage.setOnClickListener {
            val languages = langMap.keys.toTypedArray()
            AlertDialog.Builder(this)
                .setTitle("اختر لغة الترجمة")
                .setItems(languages) { _, which ->
                    val selected = languages[which]
                    targetLangCode = langMap[selected]!!
                    binding.btnSelectLanguage.text = "ترجمة إلى: $selected"
                }.show()
        }

        binding.btnTranslate.setOnClickListener {
            val text = binding.etInput.text.toString()
            if (text.isNotEmpty()) {
                performTranslation(text)
            } else {
                Toast.makeText(this, "أدخل نصاً أولاً", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performTranslation(text: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ARABIC)
            .setTargetLanguage(targetLangCode)
            .build()
        val translator = Translation.getClient(options)
        
        binding.tvOutput.text = "جاري الترجمة..."
        
        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(text).addOnSuccessListener { translatedText ->
                binding.tvOutput.text = translatedText
            }.addOnFailureListener { binding.tvOutput.text = "فشلت الترجمة" }
        }.addOnFailureListener { 
            Toast.makeText(this, "تأكد من الاتصال بالإنترنت لتحميل اللغة", Toast.LENGTH_SHORT).show()
        }
    }
}
