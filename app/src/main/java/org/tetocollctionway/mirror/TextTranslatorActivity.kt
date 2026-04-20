package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.*

class TextTranslatorActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var etInput: EditText
    private lateinit var tvOutput: TextView
    private lateinit var tts: TextToSpeech
    private var selectedLanguageCode = TranslateLanguage.ENGLISH
    private var isTranslationDone = false
    private val SPEECH_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translator)

        etInput = findViewById(R.id.et_input_text)
        tvOutput = findViewById(R.id.tv_translated_text)
        val btnSelectLang = findViewById<Button>(R.id.btn_select_language)
        val btnMic = findViewById<ImageButton>(R.id.btn_mic)
        val btnSpeak = findViewById<ImageButton>(R.id.btn_speak)
        val btnCopy = findViewById<ImageButton>(R.id.btn_copy)
        val btnShare = findViewById<ImageButton>(R.id.btn_share_audio)

        tts = TextToSpeech(this, this)

        etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (isTranslationDone) {
                    clearFields()
                    isTranslationDone = false
                }
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        btnSelectLang.setOnClickListener { showLanguageDialog(btnSelectLang) }
        btnMic.setOnClickListener { clearFields(); startSpeechToText() }

        btnShare.setOnClickListener {
            val textToShare = tvOutput.text.toString()
            if (textToShare.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, textToShare + "\n\nتمت الترجمة بواسطة ميرور سكربيون")
                }
                startActivity(Intent.createChooser(intent, "مشاركة الترجمة"))
            }
        }
    }

    private fun showLanguageDialog(btn: Button) {
        val languages = arrayOf("الإنجليزية", "التركية", "الألمانية")
        val codes = arrayOf(TranslateLanguage.ENGLISH, TranslateLanguage.TURKISH, TranslateLanguage.GERMAN)
        android.app.AlertDialog.Builder(this)
            .setTitle("اختر لغة الترجمة")
            .setItems(languages) { _, which ->
                selectedLanguageCode = codes[which]
                btn.text = "مترجم إلى: ${languages[which]}"
            }
            .show()
    }

    private fun startSpeechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    private fun clearFields() {
        etInput.text.clear()
        tvOutput.text = ""
    }

    override fun onInit(status: Int) {}
    override fun onDestroy() {
        if (::tts.isInitialized) { tts.stop(); tts.shutdown() }
        super.onDestroy()
    }
}
