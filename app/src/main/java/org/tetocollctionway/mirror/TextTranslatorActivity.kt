package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.tetocollctionway.mirror.databinding.ActivityTextTranslatorBinding
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.*

class TextTranslatorActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityTextTranslatorBinding
    private lateinit var tts: TextToSpeech
    private var targetLangCode = TranslateLanguage.TURKISH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextTranslatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)

        // اختيار اللغة (الـ 100 لغة سنضيفهم تدريجياً، نبدأ بالأهم)
        binding.btnLanguageSelector.setOnClickListener {
            val languages = arrayOf("التركية", "الإنجليزية", "البنغالية", "الهندية", "السيرلانكية")
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

        // تفعيل المايك (الالتقاط والتحرير)
        binding.btnMic.setOnClickListener {
            binding.etInput.text.clear()
            binding.tvOutput.text = ""
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            startActivityForResult(intent, 100)
        }

        // نطق الجمل المترجمة
        binding.btnSpeaker.setOnClickListener {
            val text = binding.tvOutput.text.toString()
            if (text.isNotEmpty()) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }

        // نسخ النص
        binding.btnCopy.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = android.content.ClipData.newPlainText("Mirror Translation", binding.tvOutput.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "تم نسخ النص المترجم", Toast.LENGTH_SHORT).show()
        }

        // مشاركة ملف الصوت (بجملة ميرور سكربيون)
        binding.btnShare.setOnClickListener {
            Toast.makeText(this, "تمت الترجمة بواسطة ميرور سكربيون - جاري تجهيز الملف", Toast.LENGTH_SHORT).show()
            // كود تشفير الصوت سيضاف لاحقاً لضمان توقيع التطبيق
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.etInput.setText(result?.get(0))
            performTranslation(result?.get(0) ?: "")
        }
    }

    private fun performTranslation(text: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ARABIC)
            .setTargetLanguage(targetLangCode)
            .build()
        val translator = Translation.getClient(options)
        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(text).addOnSuccessListener { translatedText ->
                binding.tvOutput.text = translatedText
            }
        }
    }

    override fun onInit(status: Int) {}
}
