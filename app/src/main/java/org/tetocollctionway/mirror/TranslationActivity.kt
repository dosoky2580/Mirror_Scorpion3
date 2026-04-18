package org.tetocollctionway.mirror

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TranslationActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    // القائمة الذهبية: 4 عائلة + 1 مستخدم
    private val voiceChoices = listOf("سيف", "سلمى", "سما", "سارة", "صوتي الخاص")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation)

        tts = TextToSpeech(this, this)
        val spinnerVoice = findViewById<Spinner>(R.id.spinnerVoice)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, voiceChoices)
        spinnerVoice.adapter = adapter
        
        // هنا الكود سيقوم بربط اختيار اللغة داخل الشاشة
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) tts?.language = Locale("tr")
    }
}
