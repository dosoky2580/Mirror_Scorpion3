package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class MainActivity : AppCompatActivity() {

    // خريطة اللغات (اسم اللغة مقابل الكود الخاص بها في ML Kit)
    private val languageMap = mapOf(
        "Arabic" to TranslateLanguage.ARABIC,
        "Turkish" to TranslateLanguage.TURKISH,
        "English" to TranslateLanguage.ENGLISH,
        "French" to TranslateLanguage.FRENCH,
        "German" to TranslateLanguage.GERMAN,
        "Russian" to TranslateLanguage.RUSSIAN,
        "Spanish" to TranslateLanguage.SPANISH,
        "Italian" to TranslateLanguage.ITALIAN,
        "Chinese" to TranslateLanguage.CHINESE,
        "Japanese" to TranslateLanguage.JAPANESE,
        "Korean" to TranslateLanguage.KOREAN
        // يمكننا إضافة الـ 100 لغة هنا تدريجياً
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputText = findViewById<EditText>(R.id.inputText)
        val outputText = findViewById<TextView>(R.id.outputText)
        val btnTranslate = findViewById<Button>(R.id.btnTranslate)
        val sourceSpinner = findViewById<Spinner>(R.id.sourceLanguageSpinner)
        val targetSpinner = findViewById<Spinner>(R.id.targetLanguageSpinner)

        val langList = languageMap.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, langList)
        
        sourceSpinner.adapter = adapter
        targetSpinner.adapter = adapter

        // وضع افتراضي: من العربية للتركية
        sourceSpinner.setSelection(langList.indexOf("Arabic"))
        targetSpinner.setSelection(langList.indexOf("Turkish"))

        btnTranslate.setOnClickListener {
            val text = inputText.text.toString()
            val sourceLang = languageMap[sourceSpinner.selectedItem.toString()] ?: TranslateLanguage.ENGLISH
            val targetLang = languageMap[targetSpinner.selectedItem.toString()] ?: TranslateLanguage.TURKISH

            if (text.isEmpty()) return@setOnClickListener

            outputText.text = "جاري تحضير المترجم..."

            val options = TranslatorOptions.Builder()
                .setSourceLanguage(sourceLang)
                .setTargetLanguage(targetLang)
                .build()

            val translator = Translation.getClient(options)
            
            translator.downloadModelIfNeeded()
                .addOnSuccessListener {
                    outputText.text = "جاري الترجمة..."
                    translator.translate(text)
                        .addOnSuccessListener { translatedText ->
                            outputText.text = translatedText
                        }
                        .addOnFailureListener { e ->
                            outputText.text = "خطأ في الترجمة: ${e.message}"
                        }
                }
                .addOnFailureListener { e ->
                    outputText.text = "فشل تحميل حزمة اللغة: ${e.message}"
                }
        }
    }
}
