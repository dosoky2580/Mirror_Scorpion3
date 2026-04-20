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
    private var langTopCode = "en"    // لغة الطرف الآخر
    private var langBottomCode = "ar" // لغتك أنت
    private val REQ_CODE_TOP = 101
    private val REQ_CODE_BOTTOM = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_translator)

        val btnMicTop = findViewById<ImageButton>(R.id.btn_mic_top)
        val btnMicBottom = findViewById<ImageButton>(R.id.btn_mic_bottom)
        val btnLangTop = findViewById<Button>(R.id.btn_lang_top)
        val btnLangBottom = findViewById<Button>(R.id.btn_lang_bottom)

        tts = TextToSpeech(this, this)

        // إعداد لغات الأزرار
        btnLangTop.text = "الطرف الآخر: English"
        btnLangBottom.text = "أنا: العربية"

        // مايك الطرف الآخر (يترجم من لغته إلى لغتك)
        btnMicTop.setOnClickListener {
            startVoiceCapture(langTopCode, REQ_CODE_TOP)
        }

        // مايك خاص بك (يترجم من لغتك إلى لغته)
        btnMicBottom.setOnClickListener {
            startVoiceCapture(langBottomCode, REQ_CODE_BOTTOM)
        }
    }

    private fun startVoiceCapture(langCode: String, requestCode: Int) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, langCode)
        try {
            startActivityForResult(intent, requestCode)
        } catch (e: Exception) {
            Toast.makeText(this, "المحرك الصوتي غير جاهز", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
            
            if (requestCode == REQ_CODE_TOP) {
                // الطرف الآخر تكلم بالإنجليزية -> الترجمة للعربية ونطقها لك
                Toast.makeText(this, "هو قال: $result", Toast.LENGTH_LONG).show()
                speakOut(result, langBottomCode) 
            } else if (requestCode == REQ_CODE_BOTTOM) {
                // أنت تكلمت بالعربية -> الترجمة للإنجليزية ونطقها له
                Toast.makeText(this, "أنت قلت: $result", Toast.LENGTH_LONG).show()
                speakOut(result, langTopCode)
            }
        }
    }

    private fun speakOut(text: String, targetLang: String) {
        tts.language = Locale(targetLang)
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) tts.language = Locale.getDefault()
    }

    override fun onDestroy() {
        if (::tts.isInitialized) { tts.stop(); tts.shutdown() }
        super.onDestroy()
    }
}
