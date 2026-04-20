package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class DialogTranslatorActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var btnLangRight: Button
    private lateinit var btnLangLeft: Button
    private lateinit var tvInputUpper: TextView
    private lateinit var tvOutputLower: TextView
    
    private var langRightCode = "en"
    private var langLeftCode = "ar"
    private val REQ_CODE_RIGHT = 101
    private val REQ_CODE_LEFT = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_translator)

        btnLangRight = findViewById(R.id.btn_lang_top) // سنعتبره الزر اليمين برمجياً
        btnLangLeft = findViewById(R.id.btn_lang_bottom) // الزر اليسار
        tvInputUpper = findViewById(R.id.et_input_text) // المحرر العلوي
        tvOutputLower = findViewById(R.id.tv_translated_text) // المحرر السفلي
        
        val btnMic = findViewById<ImageButton>(R.id.btn_mic_bottom)
        val btnSwap = findViewById<ImageButton>(R.id.btn_mic_top) // سنستخدمه حالياً كزر تبديل

        tts = TextToSpeech(this, this)

        updateButtonLabels()

        // زر التبديل: يبدل الأكواد والأسماء بين اليمين واليسار
        btnSwap.setOnClickListener {
            val tempCode = langRightCode
            langRightCode = langLeftCode
            langLeftCode = tempCode
            updateButtonLabels()
            clearScreens()
            Toast.makeText(this, "تم تبديل اللغات", Toast.LENGTH_SHORT).show()
        }

        // المايك: دايماً بياخد اللغة من الزر اليمين ويحررها فوق ويترجمها تحت للزر اليسار
        btnMic.setOnClickListener {
            clearScreens()
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, langRightCode)
            startActivityForResult(intent, REQ_CODE_RIGHT)
        }
    }

    private fun updateButtonLabels() {
        btnLangRight.text = "من: " + getLangName(langRightCode)
        btnLangLeft.text = "إلى: " + getLangName(langLeftCode)
    }

    private fun getLangName(code: String): String {
        return when(code) {
            "en" -> "English"
            "ar" -> "العربية"
            "tr" -> "Türkçe"
            else -> code
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
            
            // المحرر العلوي دايماً يتبع الزر اليمين مهما تم التبديل
            tvInputUpper.text = result
            
            // محاكاة الترجمة للمحرر السفلي بناءً على الزر اليسار
            tvOutputLower.text = "Translating to ${getLangName(langLeftCode)}..."
            
            // نطق الترجمة فوراً
            tts.language = Locale(langLeftCode)
            tts.speak(tvOutputLower.text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    private fun clearScreens() {
        tvInputUpper.text = ""
        tvOutputLower.text = ""
    }

    override fun onInit(status: Int) {}
    override fun onDestroy() {
        if (::tts.isInitialized) { tts.stop(); tts.shutdown() }
        super.onDestroy()
    }
}
