package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.*

class TextTranslatorActivity : AppCompatActivity() {
    private lateinit var etInput: EditText
    private lateinit var etOutput: EditText
    private lateinit var spinner: Spinner
    private var targetLang = TranslateLanguage.TURKISH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translator)

        etInput = findViewById(R.id.et_input_text)
        etOutput = findViewById(R.id.et_output_text)
        spinner = findViewById(R.id.spinner_languages)
        val btnMic = findViewById<ImageButton>(R.id.btn_mic)

        // إعداد 100 لغة (تلقائي من ML Kit)
        val languages = TranslateLanguage.getAllLanguages()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages)
        spinner.adapter = adapter

        btnMic.setOnClickListener { startSpeech() }
    }

    private fun startSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-SA")
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val text = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
            etInput.setText(text)
            translate(text)
        }
    }

    private fun translate(text: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ARABIC)
            .setTargetLanguage(TranslateLanguage.fromLanguageTag(spinner.selectedItem.toString()) ?: TranslateLanguage.TURKISH)
            .build()
        val client = Translation.getClient(options)
        client.downloadModelIfNeeded().addOnSuccessListener {
            client.translate(text).addOnSuccessListener { etOutput.setText(it) }
        }
    }
}
