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
    private var targetLang = TranslateLanguage.TURKISH 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextTranslatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // اللغات المطلوبة مبدئياً (مع إمكانية جلب القائمة كاملة من جوجل لاحقاً)
        val langMap = mapOf(
            "التركية" to TranslateLanguage.TURKISH,
            "الإنجليزية" to TranslateLanguage.ENGLISH,
            "البنغالية" to TranslateLanguage.BENGALI,
            "الهندية" to TranslateLanguage.HINDI,
            "السيرلانكية (السينهالية)" to TranslateLanguage.SINHALA,
            "الفرنسية" to TranslateLanguage.FRENCH,
            "الألمانية" to TranslateLanguage.GERMAN
        )

        binding.btnSelectLanguage.setOnClickListener {
            val languages = langMap.keys.toTypedArray()
            AlertDialog.Builder(this)
                .setTitle("اختر لغة الترجمة")
                .setItems(languages) { _, which ->
                    val selected = languages[which]
                    targetLang = langMap[selected]!!
                    binding.btnSelectLanguage.text = "ترجمة إلى: $selected"
                }.show()
        }

        binding.btnTranslate.setOnClickListener {
            val text = binding.etInput.text.toString()
            if (text.isNotEmpty()) {
                performTranslation(text)
            }
        }
    }

    private fun performTranslation(text: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ARABIC)
            .setTargetLanguage(targetLang)
            .build()
        val translator = Translation.getClient(options)
        
        // محرك جوجل يحمل الموديل إذا لم يكن موجوداً
        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(text).addOnSuccessListener { translatedText ->
                binding.tvOutput.text = translatedText
            }
        }.addOnFailureListener {
            Toast.makeText(this, "يرجى التأكد من الاتصال بالإنترنت لتحميل اللغة", Toast.LENGTH_SHORT).show()
        }
    }
}
