package com.tetocollctionway.mirror.core

import android.widget.EditText
import android.speech.tts.TextToSpeech

class MirrorTranslatorEngine(private val tts: TextToSpeech) {

    fun resetForNewTask(input: EditText, output: EditText) {
        input.text.clear()
        output.text.clear()
    }

    fun speakWithSignature(text: String) {
        val signature = "تمت الترجمة بواسطة ميرور سكربيون"
        val fullText = "$text . $signature"
        tts.speak(fullText, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, null)
    }
}
