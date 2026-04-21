package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.tetocollctionway.mirror.databinding.ActivityDialogTranslatorBinding
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.Locale

class DialogTranslatorActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityDialogTranslatorBinding
    private var tts: TextToSpeech? = null
    private var rightLang = TranslateLanguage.ARABIC
    private var leftLang = TranslateLanguage.TURKISH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogTranslatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)

        binding.btnMain_mic.setOnClickListener {
            binding.etUpperDialog.setText("")
            binding.tvLowerDialog.text = ""
            startSpeech()
        }

        binding.btnSwap.setOnClickListener {
            val temp = rightLang
            rightLang = leftLang
            leftLang = temp
            val tempText = binding.btnLangRight.text
            binding.btnLangRight.text = binding.btnLangLeft.text
            binding.btnLangLeft.text = tempText
        }
    }

    private fun startSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, if(rightLang == TranslateLanguage.ARABIC) "ar-SA" else "tr-TR")
        startActivityForResult(intent, 200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == RESULT_OK) {
            val text = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
            binding.etUpperDialog.setText(text)
            translateDialog(text)
        }
    }

    private fun translateDialog(text: String) {
        val options = TranslatorOptions.Builder().setSourceLanguage(rightLang).setTargetLanguage(leftLang).build()
        val translator = Translation.getClient(options)
        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(text).addOnSuccessListener { 
                binding.tvLowerDialog.text = it 
                tts?.speak(it, TextToSpeech.QUEUE_FLUSH, null, "")
            }
        }
    }

    override fun onInit(status: Int) {}
}
