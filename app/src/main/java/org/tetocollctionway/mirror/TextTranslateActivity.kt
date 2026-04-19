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
import java.util.*

class TextTranslateActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var etSource: EditText
    private lateinit var tvTarget: TextView
    private lateinit var btnMic: ImageButton
    private lateinit var btnSpeaker: ImageButton
    private lateinit var btnCopy: ImageButton
    private lateinit var btnShare: ImageButton
    private var tts: TextToSpeech? = null
    private val SPEECH_REQUEST_CODE = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translate)

        etSource = findViewById(R.id.etSource)
        tvTarget = findViewById(R.id.tvTarget)
        btnMic = findViewById(R.id.btnMic)
        btnSpeaker = findViewById(R.id.btnSpeaker)
        btnCopy = findViewById(R.id.btnCopy)
        btnShare = findViewById(R.id.btnShare)

        tts = TextToSpeech(this, this)

        btnMic.setOnClickListener { startSpeechToText() }

        // زر النطق
        btnSpeaker.setOnClickListener {
            val text = tvTarget.text.toString()
            if (text.isNotEmpty() && text != "الترجمة...") {
                tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }

        // زر النسخ
        btnCopy.setOnClickListener {
            val text = tvTarget.text.toString()
            if (text.isNotEmpty()) {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Mirror Translation", text)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "تم نسخ النص", Toast.LENGTH_SHORT).show()
            }
        }

        // زر المشاركة (توقيع ميرور سكربيون)
        btnShare.setOnClickListener {
            val text = tvTarget.text.toString()
            if (text.isNotEmpty()) {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "$text\n\n(تمت الترجمة بواسطة ميرور سكربيون)")
                }
                startActivity(Intent.createChooser(shareIntent, "مشاركة الترجمة"))
            }
        }
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
            // هنا سيتم استدعاء محرك الترجمة تلقائياً لاحقاً
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) tts?.language = Locale("ar")
    }

    private fun clearFields() {
        etSource.text.clear()
        tvTarget.text = ""
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }
}
