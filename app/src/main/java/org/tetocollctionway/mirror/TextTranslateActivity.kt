package org.tetocollctionway.mirror

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.nl.translate.TranslateLanguage
import java.util.*

class TextTranslateActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var etSource: EditText
    private lateinit var tvTarget: TextView
    private lateinit var btnMic: ImageButton
    private lateinit var btnSpeaker: ImageButton
    private lateinit var btnCopy: ImageButton
    private lateinit var btnShare: ImageButton
    private lateinit var btnSelectLanguage: Button
    
    private var tts: TextToSpeech? = null
    private val SPEECH_REQUEST_CODE = 102
    private var isTranslationDone = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translate)

        // ربط التروس
        etSource = findViewById(R.id.etSource)
        tvTarget = findViewById(R.id.tvTarget)
        btnMic = findViewById(R.id.btnMic)
        btnSpeaker = findViewById(R.id.btnSpeaker)
        btnCopy = findViewById(R.id.btnCopy)
        btnShare = findViewById(R.id.btnShare)
        btnSelectLanguage = findViewById(R.id.btnSelectLanguage)

        tts = TextToSpeech(this, this)

        // 1. منطق المايك (الالتقاط + المسح التلقائي)
        btnMic.setOnClickListener {
            startSpeechToText()
        }

        // 2. منطق الكيبورد (المسح عند البدء الجديد)
        etSource.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && isTranslationDone) {
                clearFields()
            }
        }

        // 3. زر الترجمة (تفعيل المحرك)
        // سنستخدم ترجمة فورية عند تغيير النص لسرعة الـ 3 ثواني
        btnSelectLanguage.setOnClickListener {
            // هنا سيتم فتح قائمة الـ 100 لغة لاحقاً، حالياً سنثبت EN -> AR للتجربة
            Toast.makeText(this, "قائمة الـ 100 لغة جاهزة للربط", Toast.LENGTH_SHORT).show()
        }

        btnSpeaker.setOnClickListener { speakText() }
        btnCopy.setOnClickListener { copyToClipboard() }
        btnShare.setOnClickListener { shareAudioStamp() }
    }

    private fun startSpeechToText() {
        clearFields()
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val spokenText = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
            etSource.setText(spokenText)
            executeTranslation(spokenText)
        }
    }

    private fun executeTranslation(text: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.ARABIC)
            .build()
        val translator = Translation.getClient(options)

        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                translator.translate(text)
                    .addOnSuccessListener { result ->
                        tvTarget.text = result
                        isTranslationDone = true
                    }
            }
    }

    private fun speakText() {
        val text = tvTarget.text.toString()
        if (text.isNotEmpty()) tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun copyToClipboard() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("Mirror", tvTarget.text))
        Toast.makeText(this, "تم النسخ", Toast.LENGTH_SHORT).show()
    }

    private fun shareAudioStamp() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "${tvTarget.text}\n\nتمت الترجمة بواسطة ميرور سكربيون")
        }
        startActivity(Intent.createChooser(shareIntent, "مشاركة"))
    }

    private fun clearFields() {
        etSource.text.clear()
        tvTarget.text = ""
        isTranslationDone = false
    }

    override fun onInit(status: Int) { if (status == TextToSpeech.SUCCESS) tts?.language = Locale("ar") }
    override fun onDestroy() { tts?.stop(); tts?.shutdown(); super.onDestroy() }
}
