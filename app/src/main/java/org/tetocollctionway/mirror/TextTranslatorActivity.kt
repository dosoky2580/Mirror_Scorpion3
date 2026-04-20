package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TextTranslatorActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var etInput: EditText
    private lateinit var tvOutput: TextView
    private lateinit var tts: TextToSpeech
    private var selectedLanguageCode = "en" // اللغة الافتراضية
    private val SPEECH_REQUEST_CODE = 100

    // مصفوفة عينة من اللغات (سنوسعها لتشمل الـ 100 لغة لاحقاً)
    private val languages = arrayOf("الإنجليزية", "التركية", "الفرنسية", "الألمانية", "الإيطالية", "الإسبانية", "الصينية", "اليابانية")
    private val languageCodes = arrayOf("en", "tr", "fr", "de", "it", "es", "zh", "ja")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translator)

        etInput = findViewById(R.id.et_input_text)
        tvOutput = findViewById(R.id.tv_translated_text)
        val btnSelectLang = findViewById<Button>(R.id.btn_select_language)
        val btnMic = findViewById<ImageButton>(R.id.btn_mic)
        val btnSpeak = findViewById<ImageButton>(R.id.btn_speak)
        val btnCopy = findViewById<ImageButton>(R.id.btn_copy)

        tts = TextToSpeech(this, this)

        // اختيار اللغة من القائمة
        btnSelectLang.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("اختر لغة الترجمة")
            builder.setItems(languages) { _, which ->
                selectedLanguageCode = languageCodes[which]
                btnSelectLang.text = "مترجم إلى: ${languages[which]}"
                Toast.makeText(this, "تم اختيار ${languages[which]}", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }

        btnMic.setOnClickListener {
            clearFields()
            startSpeechToText()
        }

        btnSpeak.setOnClickListener {
            val text = tvOutput.text.toString()
            if (text.isNotEmpty()) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }

        btnCopy.setOnClickListener {
            val text = tvOutput.text.toString()
            if (text.isNotEmpty()) {
                val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = android.content.ClipData.newPlainText("MirrorTranslation", text)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "تم نسخ النص", Toast.LENGTH_SHORT).show()
            }
        }
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
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val recognizedText = result?.get(0) ?: ""
            etInput.setText(recognizedText)
            // هنا سنستدعي محرك الترجمة الفعلي في الخطوة القادمة
            tvOutput.text = "جاري الترجمة إلى ${selectedLanguageCode}..." 
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.US
        }
    }

    private fun clearFields() {
        etInput.text.clear()
        tvOutput.text = ""
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}
