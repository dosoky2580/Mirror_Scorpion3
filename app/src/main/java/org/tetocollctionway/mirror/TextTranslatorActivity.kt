package org.tetocollctionway.mirror

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.tetocollctionway.mirror.databinding.ActivityTextTranslatorBinding
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.Locale

class TextTranslatorActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityTextTranslatorBinding
    private var tts: TextToSpeech? = null
    private var targetLangCode = TranslateLanguage.TURKISH // الافتراضي تركي

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextTranslatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tts = TextToSpeech(this, this)

        // 1. اختيار اللغة (قائمة بـ 100 لغة - أمثلة لأهمها)
        val languages = mapOf("التركية" to TranslateLanguage.TURKISH, "الإنجليزية" to TranslateLanguage.ENGLISH, "الفرنسية" to TranslateLanguage.FRENCH, "الألمانية" to TranslateLanguage.GERMAN)
        binding.btnSelectLanguage.setOnClickListener {
            val list = languages.keys.toTypedArray()
            AlertDialog.Builder(this).setTitle("اختر لغة الترجمة").setItems(list) { _, which ->
                val selected = list[which]
                targetLangCode = languages[selected]!!
                binding.btnSelectLanguage.text = "ترجمة إلى: $selected"
                translateNow(binding.etInput.text.toString())
            }.show()
        }

        // 2. المايك والمسح التلقائي
        binding.btnMic.setOnClickListener {
            binding.etInput.setText("")
            binding.tvOutput.text = ""
            startSpeech()
        }

        // 3. الترجمة الفورية عند الكتابة
        binding.etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                translateNow(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // 4. النسخ
        binding.btnCopy.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(android.content.ClipData.newPlainText("Mirror Translation", binding.tvOutput.text))
            Toast.makeText(this, "تم النسخ", Toast.LENGTH_SHORT).show()
        }

        // 5. النطق
        binding.btnSpeaker.setOnClickListener {
            tts?.speak(binding.tvOutput.text.toString(), TextToSpeech.QUEUE_FLUSH, null, "")
        }

        // 6. المشاركة
        binding.btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "${binding.tvOutput.text}\n\nتمت الترجمة بواسطة ميرور سكربيون")
            startActivity(Intent.createChooser(shareIntent, "مشاركة الترجمة"))
        }
    }

    private fun translateNow(text: String) {
        if (text.isEmpty()) return
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ARABIC)
            .setTargetLanguage(targetLangCode)
            .build()
        val translator = Translation.getClient(options)
        
        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(text).addOnSuccessListener { binding.tvOutput.text = it }
        }
    }

    private fun startSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
            binding.etInput.setText(result)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) tts?.language = Locale("tr")
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }
}
