package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.tetocollctionway.mirror.databinding.ActivityTextTranslatorBinding
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class TextTranslatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTextTranslatorBinding
    private var targetLang = TranslateLanguage.TURKISH // الافتراضي تركي

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextTranslatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // قائمة اللغات (الـ 4 لغات الأساسية حالياً وقابلة للزيادة لـ 100)
        val langMap = mapOf(
            "التركية" to TranslateLanguage.TURKISH,
            "الإنجليزية" to TranslateLanguage.ENGLISH,
            "الفرنسية" to TranslateLanguage.FRENCH,
            "الألمانية" to TranslateLanguage.GERMAN,
            "الإيطالية" to TranslateLanguage.ITALIAN
        )

        binding.btnSelectLanguage.setOnClickListener {
            val languages = langMap.keys.toTypedArray()
            AlertDialog.Builder(this)
                .setTitle("اختر لغة الترجمة")
                .setItems(languages) { _, which ->
                    val selected = languages[which]
                    targetLang = langMap[selected]!!
                    binding.btnSelectLanguage.text = "ترجمة إلى: $selected"
                    Toast.makeText(this, "تم اختيار $selected", Toast.LENGTH_SHORT).show()
                }.show()
        }

        binding.btnTranslate.setOnClickListener {
            val text = binding.etInput.text.toString()
            if (text.isNotEmpty()) {
                performTranslation(text)
            } else {
                Toast.makeText(this, "من فضلك أدخل نصاً أولاً", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performTranslation(text: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ARABIC)
            .setTargetLanguage(targetLang)
            .build()
        val translator = Translation.getClient(options)
        
        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(text).addOnSuccessListener { translatedText ->
                binding.tvOutput.text = translatedText
            }
        }.addOnFailureListener {
            Toast.makeText(this, "فشل في تحميل محرك اللغة", Toast.LENGTH_SHORT).show()
        }
    }
}
