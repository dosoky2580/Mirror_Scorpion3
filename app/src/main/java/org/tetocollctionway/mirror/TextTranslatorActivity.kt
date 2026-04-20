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
    private var targetLangCode = TranslateLanguage.TURKISH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translator)

        etInput = findViewById(R.id.et_input_text)
        etOutput = findViewById(R.id.et_output_text)
        spinner = findViewById(R.id.spinner_languages)
        val btnMic = findViewById<ImageButton>(R.id.btn_mic)

        // إعداد قائمة اللغات (100 لغة تقريباً مدعومة من ML Kit)
        val languages = arrayOf("التركية", "الإنجليزية", "الألمانية", "الفرنسية", "الإيطالية", "الإسبانية", "الروسية")
        val codes = arrayOf(TranslateLanguage.TURKISH, TranslateLanguage.ENGLISH, TranslateLanguage.GERMAN, TranslateLanguage.FRENCH, TranslateLanguage.ITALIAN, TranslateLanguage.SPANISH, TranslateLanguage.RUSSIAN)
        
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                targetLangCode = codes[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        btnMic.setOnClickListener { startSpeech() }
    }

    private fun startSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-SA")
        try { startActivityForResult(intent, 100) } catch (e: Exception) {}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val text = result?.get(0) ?: ""
            etInput.setText(text)
            translateText(text)
        }
    }

    private fun translateText(text: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ARABIC)
            .setTargetLanguage(targetLangCode)
            .build()
        val translator = Translation.getClient(options)
        etOutput.setText("جاري الترجمة...")
        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(text).addOnSuccessListener { etOutput.setText(it) }
        }
    }
}
