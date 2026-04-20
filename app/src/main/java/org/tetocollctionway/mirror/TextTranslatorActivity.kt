package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TextTranslatorActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var etInput: EditText
    private lateinit var tvOutput: TextView
    private lateinit var tts: TextToSpeech
    private var selectedLanguageCode = "en"
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

        // المسح التلقائي عند لمس الكيبورد بعد الترجمة
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

        btnSelectLang.setOnClickListener {
            showLanguageDialog(btnSelectLang)
        }

        btnMic.setOnClickListener {
            clearFields()
            startSpeechToText()
        }

        btnSpeak.setOnClickListener {
            val text = tvOutput.text.toString()
            if (text.isNotEmpty()) {
                tts.language = Locale(selectedLanguageCode)
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }

        btnShare.setOnClickListener {
            val textToShare = tvOutput.text.toString()
            if (textToShare.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                // إضافة توقيع ميرور كما طلبت
                val signature = "\n\nتمت الترجمة بواسطة ميرور سكربيون"
                intent.putExtra(Intent.EXTRA_TEXT, textToShare + signature)
                startActivity(Intent.createChooser(intent, "مشاركة الترجمة"))
            }
        }

        btnCopy.setOnClickListener {
            val text = tvOutput.text.toString()
            if (text.isNotEmpty()) {
                val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
                clipboard.setPrimaryClip(android.content.ClipData.newPlainText("Mirror", text))
                Toast.makeText(this, "تم النسخ بنجاح", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLanguageDialog(btn: Button) {
        val languages = arrayOf("الإنجليزية", "التركية", "العربية", "الفرنسية", "الألمانية", "الإيطالية", "الإسبانية")
        val codes = arrayOf("en", "tr", "ar", "fr", "de", "it", "es")
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("اختر لغة الترجمة")
        builder.setItems(languages) { _, which ->
            selectedLanguageCode = codes[which]
            btn.text = "مترجم إلى: ${languages[which]}"
        }
        builder.show()
    }

    private fun startSpeechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            val recognizedText = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
            etInput.setText(recognizedText)
            tvOutput.text = "جاري الترجمة..." // سيتم ربط الـ API الفعلي هنا
            isTranslationDone = true
        }
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
