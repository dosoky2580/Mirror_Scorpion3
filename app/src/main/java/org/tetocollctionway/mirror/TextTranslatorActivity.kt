package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TextTranslatorActivity : AppCompatActivity() {

    private lateinit var etInput: EditText
    private lateinit var tvOutput: TextView
    private val SPEECH_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translator)

        val btnSelectLang = findViewById<Button>(R.id.btn_select_language)
        etInput = findViewById(R.id.et_input_text)
        tvOutput = findViewById(R.id.tv_translated_text)
        val btnMic = findViewById<ImageButton>(R.id.btn_mic)
        val btnSpeak = findViewById<ImageButton>(R.id.btn_speak)
        val btnCopy = findViewById<ImageButton>(R.id.btn_copy)

        // 1. منطق المايك - التقاط الكلام
        btnMic.setOnClickListener {
            // تنفيذ وصفك: عند بدء مايك جديد يتم مسح المحررين
            clearFields()
            startSpeechToText()
        }

        // 2. منطق المسح التلقائي عند الكتابة (بداية ترجمة جديدة)
        etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // إذا بدأ المستخدم في الكتابة يدوياً، يمكننا هنا تفعيل محرك الترجمة الفوري
                if (s.isNullOrEmpty()) tvOutput.text = ""
            }
            override fun afterTextChanged(s: Editable?) {
                // هنا سيتم استدعاء محرك الترجمة الذكي (الذي سنربطه في الخطوة القادمة)
            }
        })

        // 3. زر النسخ
        btnCopy.setOnClickListener {
            val text = tvOutput.text.toString()
            if (text.isNotEmpty()) {
                val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = android.content.ClipData.newPlainText("label", text)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "تم نسخ النص المترجم", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startSpeechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        } catch (e: Exception) {
            Toast.makeText(this, "المايك غير مدعوم في جهازك", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            etInput.setText(result?.get(0))
        }
    }

    private fun clearFields() {
        etInput.text.clear()
        tvOutput.text = ""
    }
}
