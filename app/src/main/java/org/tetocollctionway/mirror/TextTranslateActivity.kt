package org.tetocollctionway.mirror

import android.app.Activity
import android.app.AlertDialog
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
    private var selectedTargetLang = TranslateLanguage.ARABIC
    private var selectedLangCode = "ar"

    // قائمة اللغات (عينة من الـ 100 لغة لضمان استقرار البناء)
    private val languagesMap = mapOf(
        "العربية" to Pair(TranslateLanguage.ARABIC, "ar"),
        "الإنجليزية" to Pair(TranslateLanguage.ENGLISH, "en"),
        "التركية" to Pair(TranslateLanguage.TURKISH, "tr"),
        "الفرنسية" to Pair(TranslateLanguage.FRENCH, "fr"),
        "الألمانية" to Pair(TranslateLanguage.GERMAN, "de"),
        "الإسبانية" to Pair(TranslateLanguage.SPANISH, "es"),
        "الإيطالية" to Pair(TranslateLanguage.ITALIAN, "it"),
        "الصينية" to Pair(TranslateLanguage.CHINESE, "zh"),
        "اليابانية" to Pair(TranslateLanguage.JAPANESE, "ja"),
        "الروسية" to Pair(TranslateLanguage.RUSSIAN, "ru")
        // سيتم توسيعها لـ 100 لغة في التحديث القادم
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translate)

        etSource = findViewById(R.id.etSource)
        tvTarget = findViewById(R.id.tvTarget)
        btnMic = findViewById(R.id.btnMic)
        btnSpeaker = findViewById(R.id.btnSpeaker)
        btnCopy = findViewById(R.id.btnCopy)
        btnShare = findViewById(R.id.btnShare)
        btnSelectLanguage = findViewById(R.id.btnSelectLanguage)

        tts = TextToSpeech(this, this)

        btnSelectLanguage.setOnClickListener { showLanguageSelector() }
        btnMic.setOnClickListener { startSpeechToText() }
        
        etSource.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && isTranslationDone) clearFields()
        }

        btnSpeaker.setOnClickListener { speakText() }
        btnCopy.setOnClickListener { copyToClipboard() }
        btnShare.setOnClickListener { shareAudioStamp() }
    }

    private fun showLanguageSelector() {
        val langs = languagesMap.keys.toTypedArray()
        AlertDialog.Builder(this)
            .setTitle("اختر لغة الترجمة")
            .setItems(langs) { _, which ->
                val selectedName = langs[which]
                selectedTargetLang = languagesMap[selectedName]?.first ?: TranslateLanguage.ARABIC
                selectedLangCode = languagesMap[selectedName]?.second ?: "ar"
                btnSelectLanguage.text = "مترجم إلى: $selectedName"
                tts?.language = Locale(selectedLangCode)
            }
            .show()
    }

    private fun executeTranslation(text: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH) 
            .setTargetLanguage(selectedTargetLang)
            .build()
        val translator = Translation.getClient(options)

        tvTarget.text = "جاري الترجمة..."
        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                translator.translate(text).addOnSuccessListener { result ->
                    tvTarget.text = result
                    isTranslationDone = true
                }
            }
            .addOnFailureListener { tvTarget.text = "فشل في تحميل حزمة اللغة" }
    }

    private fun startSpeechToText() {
        clearFields()
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
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

    override fun onInit(status: Int) { if (status == TextToSpeech.SUCCESS) tts?.language = Locale(selectedLangCode) }
    override fun onDestroy() { tts?.stop(); tts?.shutdown(); super.onDestroy() }
}
