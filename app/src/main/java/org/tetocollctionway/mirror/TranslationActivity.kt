package org.tetocollctionway.mirror

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TranslationActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private lateinit var outputEditor: TextView
    private lateinit var btnSpeak: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation)
        
        tts = TextToSpeech(this, this)
        val inputEditor = findViewById<EditText>(inputEditor)
        outputEditor = findViewById(R.id.outputEditor)
        val btnMic = findViewById<ImageButton>(R.id.btnMic)
        btnSpeak = findViewById(R.id.btnSpeak)
        
        // في البداية، الاسبيكر مخفي تماماً
        btnSpeak.visibility = View.GONE

        btnMic.setOnClickListener {
            // 1. إيقاف أي نطق شغال فوراً
            if (tts?.isSpeaking == true) tts?.stop()
            
            // 2. تصفير المحررات لبدء جلسة جديدة
            inputEditor.setText("")
            outputEditor.text = ""
            btnSpeak.visibility = View.GONE
            
            Toast.makeText(this, "أدهم يستمع إليك...", Toast.LENGTH_SHORT).show()
            // سيتم وضع منطق التقاط الكلام هنا
        }

        btnSpeak.setOnClickListener {
            val textToSpeak = outputEditor.text.toString()
            if (textToSpeak.isNotEmpty() && textToSpeak != "الترجمة ستظهر هنا...") {
                tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    // دالة لتحديث واجهة الترجمة (تُستدعى عند اكتمال الترجمة)
    fun onTranslationComplete(translatedText: String) {
        outputEditor.text = translatedText
        // لا يظهر الاسبيكر إلا بعد وجود نص مترجم
        if (translatedText.isNotEmpty()) {
            btnSpeak.visibility = View.VISIBLE
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) tts?.language = Locale.getDefault()
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }
}
