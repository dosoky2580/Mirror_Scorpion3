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
    private var targetLang = TranslateLanguage.TURKISH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextTranslatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)

        val langMap = mapOf(
            "التركية" to TranslateLanguage.TURKISH,
            "الإنجليزية" to TranslateLanguage.ENGLISH,
            "البنغالية" to TranslateLanguage.BENGALI,
            "الهندية" to TranslateLanguage.HINDI,
            "السيرلانكية" to "si" 
        )

        // اختيار اللغة (الزر اللي في منتصف الشاشة العلوي)
        binding.btnLanguageSelector.setOnClickListener {
            val languages = langMap.keys.toTypedArray()
            AlertDialog.Builder(this)
                .setTitle("اختر لغة الترجمة")
                .setItems(languages) { _, which ->
                    val selected = languages[which]
                    targetLang = langMap[selected] ?: TranslateLanguage.TURKISH
                    binding.btnLanguageSelector.text = "ترجمة إلى: $selected"
                }.show()
        }

        // المسح عند بدء كتابة أو التقاط جديد كما طلبت
        val clearFields = {
            binding.etInput.text.clear()
            binding.tvOutput.text = ""
        }

        // المايك للالتقاط والتحرير
        binding.btnMic.setOnClickListener {
            clearFields()
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            startActivityForResult(intent, 100)
        }

        // النطق (الاسبيكر)
        binding.btnSpeaker.setOnClickListener {
            val text = binding.tvOutput.text.toString()
            if (text.isNotEmpty()) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }

        // النسخ
        binding.btnCopy.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = android.content.ClipData.newPlainText("Mirror", binding.tvOutput.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "تم النسخ", Toast.LENGTH_SHORT).show()
        }

        // المشاركة (ملف صوتي فقط بتوقيع ميرور)
        binding.btnShare.setOnClickListener {
            Toast.makeText(this, "تمت الترجمة بواسطة ميرور سكربيون", Toast.LENGTH_LONG).show()
            // سيتم إضافة كود توليد ملف الـ mp3 هنا
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val capturedText = result?.get(0) ?: ""
            binding.etInput.setText(capturedText)
            performTranslation(capturedText)
        }
    }

    private fun performTranslation(text: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ARABIC)
            .setTargetLanguage(targetLang)
            .build()
        val translator = Translation.getClient(options)
        
        binding.tvOutput.text = "جاري الترجمة..."
        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(text).addOnSuccessListener { translatedText ->
                binding.tvOutput.text = translatedText
            }
        }
    }

    override fun onInit(status: Int) {}
}
