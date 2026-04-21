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

        val langMap = mapOf(
            "التركية" to TranslateLanguage.TURKISH,
            "الإنجليزية" to TranslateLanguage.ENGLISH,
            "البنغالية" to TranslateLanguage.BENGALI,
            "الهندية" to TranslateLanguage.HINDI,
            "السيرلانكية" to "si", // الكود المختصر الصحيح للسينهالية
            "الفرنسية" to TranslateLanguage.FRENCH
        )

        binding.btnSelectLanguage.setOnClickListener {
            val languages = langMap.keys.toTypedArray()
            AlertDialog.Builder(this)
                .setTitle("اختر لغة الترجمة")
                .setItems(languages) { _, which ->
                    val selected = languages[which]
                    val langValue = langMap[selected]!!
                    targetLang = langValue.toString()
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
        
        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(text).addOnSuccessListener { translatedText ->
                binding.tvOutput.text = translatedText
            }
        }.addOnFailureListener {
            Toast.makeText(this, "خطأ في المحرك أو الإنترنت", Toast.LENGTH_SHORT).show()
        }
    }
}
