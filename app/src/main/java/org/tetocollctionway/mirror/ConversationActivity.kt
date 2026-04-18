package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ConversationActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private lateinit var txtMe: TextView
    private lateinit var txtOther: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)

        tts = TextToSpeech(this, this)
        txtMe = findViewById(R.id.txtMe)
        txtOther = findViewById(R.id.txtOther)

        findViewById<ImageButton>(R.id.btnMicMe).setOnClickListener {
            startListening(101) // كود خاص بميكروفونك
        }

        findViewById<ImageButton>(R.id.btnMicOther).setOnClickListener {
            startListening(102) // كود خاص بميكروفون الطرف الآخر
        }
    }

    private fun startListening(requestCode: Int) {
        if (tts?.isSpeaking == true) tts?.stop()
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, if(requestCode == 101) "ar-EG" else "tr-TR")
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
            if (requestCode == 101) {
                txtMe.text = result
                // هنا سيتم إرسال النص لمحرك الترجمة أونلاين ونطقه للتركي
            } else {
                txtOther.text = result
                // هنا سيتم إرسال النص لمحرك الترجمة أونلاين ونطقه للعربي
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) tts?.language = Locale.getDefault()
    }

    override fun onDestroy() {
        tts?.shutdown()
        super.onDestroy()
    }
}
