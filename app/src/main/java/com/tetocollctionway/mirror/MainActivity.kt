package com.tetocollctionway.mirror

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tetocollctionway.mirror.core.MirrorTranslatorEngine
import android.speech.tts.TextToSpeech
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var translatorEngine: MirrorTranslatorEngine
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        tts = TextToSpeech(this, this)
        translatorEngine = MirrorTranslatorEngine(tts)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.getDefault()
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}
