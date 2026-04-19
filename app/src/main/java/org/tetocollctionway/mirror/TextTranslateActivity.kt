package org.tetocollctionway.mirror

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TextTranslateActivity : AppCompatActivity() {

    private lateinit var etSource: EditText
    private lateinit var tvTarget: TextView
    private lateinit var btnMic: ImageButton
    private val SPEECH_REQUEST_CODE = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translate)

        etSource = findViewById(R.id.etSource)
        tvTarget = findViewById(R.id.tvTarget)
        btnMic = findViewById(R.id.btnMic)

        // محرك المايك: التقاط الكلام
        btnMic.setOnClickListener {
            startSpeechToText()
        }

        // منطق المسح التلقائي عند الكتابة من جديد (الكيبورد)
        etSource.setOnClickListener {
            // إذا كان هناك ترجمة سابقة، امسح لبدء جلسة جديدة
            if (tvTarget.text.isNotEmpty() && tvTarget.text != "الترجمة...") {
                clearFields()
            }
        }
    }

    private fun startSpeechToText() {
        // مسح المحررين لبدء التقاط جديد كما طلبت
        clearFields()
        
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "اسمعك.. تحدث الآن")
        }
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        } catch (e: Exception) {
            Toast.makeText(this, "محرك البحث الصوتي غير مدعوم", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = result?.get(0) ?: ""
            
            // تحرير النص داخل المحرر ليتأكد المستخدم ويعدل بالكيبورد
            etSource.setText(spokenText)
        }
    }

    private fun clearFields() {
        etSource.text.clear()
        tvTarget.text = ""
    }
}
