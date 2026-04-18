package org.tetocollctionway.mirror

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TranslationActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    // القائمة كما اتفقنا: 4 أصوات للعائلة
    private val familyVoices = listOf("سيف", "سلمى", "سما", "سارة")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation)

        tts = TextToSpeech(this, this)
        val spinnerVoice = findViewById<Spinner>(R.id.spinnerVoice)
        val voiceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, familyVoices)
        spinnerVoice.adapter = voiceAdapter

        // إضافة زرار اختيار اللغة هنا برمجياً أو عبر الـ XML
        Toast.makeText(this, "اختر اللغة من القائمة العلوية", Toast.LENGTH_LONG).show()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) tts?.language = Locale("tr")
    }
}
