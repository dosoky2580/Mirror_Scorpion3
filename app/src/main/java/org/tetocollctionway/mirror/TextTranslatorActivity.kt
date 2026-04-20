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
    private var selectedLanguageCode = "en" 
    private val SPEECH_REQUEST_CODE = 100

    // القائمة الكاملة لأهم اللغات (يمكن توسيعها برمجياً لـ 100)
    private val languages = arrayOf(
        "الإنجليزية", "التركية", "العربية", "الفرنسية", "الألمانية", "الإيطالية", 
        "الإسبانية", "الروسية", "الصينية", "اليابانية", "الكورية", "الهندية", 
        "الأردية", "الفارسية", "الهولندية", "السويدية", "اليونانية", "العبرية"
    )
    private val languageCodes = arrayOf(
        "en", "tr", "ar", "fr", "de", "it", "es", "ru", "zh", "ja", "ko", "hi", 
        "ur", "fa", "nl", "sv", "el", "he"
    )

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

        btnSelectLang.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("اختر لغة الهدف")
            builder.setItems(languages) { _, which ->
                selectedLanguageCode = languageCodes[which]
                btnSelectLang.text = "مترجم إلى: ${languages[which]}"
            }
            builder.show()
        }

        btnMic.setOnClickListener {
            etInput.text.clear()
            tvOutput.text = ""
            startSpeechToText()
        }

        btnSpeak.setOnClickListener {
            val text = tvOutput.text.toString()
            if (text.isNotEmpty()) {
                tts.language = Locale(selectedLanguageCode)
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }

        btnCopy.setOnClickListener {
            val text = tvOutput.text.toString()
            if (text.isNotEmpty()) {
                val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = android.content.ClipData.newPlainText("Mirror", text)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "تم النسخ", Toast.LENGTH_SHORT).show()
            }
        }
        
        btnShare.setOnClickListener {
             val text = tvOutput.text.toString()
             if (text.isNotEmpty()) {
                 val intent = Intent(Intent.ACTION_SEND)
                 intent.type = "text/plain"
                 intent.putExtra(Intent.EXTRA_TEXT, text)
                 startActivity(Intent.createChooser(intent, "مشاركة الترجمة عبر..."))
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
            val recognizedText = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
            etInput.setText(recognizedText)
            
            // محاكاة محرك الترجمة (حتى نربط الـ API الفعلي في التحديث القادم)
            tvOutput.text = "جاري معالجة النص وترجمته إلى (${selectedLanguageCode})..."
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) tts.language = Locale.getDefault()
    }

    override fun onDestroy() {
        if (::tts.isInitialized) { tts.stop(); tts.shutdown() }
        super.onDestroy()
    }
}
