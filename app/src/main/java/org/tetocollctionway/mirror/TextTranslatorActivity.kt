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
    private var targetLangCode = TranslateLanguage.TURKISH 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextTranslatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // استخدام btnLanguageSelector بدلاً من btnSelectLanguage
        binding.btnLanguageSelector.setOnClickListener {
            val languages = arrayOf("التركية", "الإنجليزية", "البنغالية", "الهندية")
            AlertDialog.Builder(this)
                .setTitle("اختر لغة الترجمة")
                .setItems(languages) { _, which ->
                    val selected = languages[which]
                    targetLangCode = when(selected) {
                        "الإنجليزية" -> TranslateLanguage.ENGLISH
                        "البنغالية" -> TranslateLanguage.BENGALI
                        "الهندية" -> TranslateLanguage.HINDI
                        else -> TranslateLanguage.TURKISH
                    }
                    binding.btnLanguageSelector.text = "ترجمة إلى: $selected"
                }.show()
        }

        // استخدام محرك الترجمة عند الكتابة (سيتم تفعيل المايك لاحقاً)
        // ربط منطق المسح عند بدء ترجمة جديدة كما طلبت
        binding.etInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.etInput.text.clear()
                binding.tvOutput.text = ""
            }
        }
    }
}
