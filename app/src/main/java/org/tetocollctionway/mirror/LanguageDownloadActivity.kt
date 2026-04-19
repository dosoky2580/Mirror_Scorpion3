package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ProgressBar
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateRemoteModel

class LanguageDownloadActivity : AppCompatActivity() {

    private val modelManager = RemoteModelManager.getInstance()
    private lateinit var statusText: TextView
    private lateinit var progressDownload: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_download)

        statusText = findViewById(R.id.statusText)
        // سنضيف ProgressBar برمجياً أو تأكد من وجوده في الـ XML
        progressDownload = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
            visibility = View.GONE
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 20)
        }

        val container = findViewById<LinearLayout>(R.id.langContainer)
        container.addView(progressDownload, 0)

        // القائمة العالمية الشاملة (لغات المصنع + لغات العالم)
        val languages = mapOf(
            "العربية" to "ar", "الإنجليزية" to "en", "الألمانية" to "de",
            "الفرنسية" to "fr", "الإيطالية" to "it", "الصينية" to "zh",
            "اليابانية" to "ja", "الروسية" to "ru", "الأوكرانية" to "uk",
            "التركية" to "tr", "البنغالية" to "bn", "الهندية" to "hi",
            "السيرلانكية" to "si"
        )

        languages.forEach { (name, code) ->
            val btn = Button(this).apply {
                text = "تنزيل ${name}"
                textSize = 20f
                setOnClickListener { downloadLanguage(code, name) }
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(0, 10, 0, 10) }
            }
            container.addView(btn)
        }
    }

    private fun downloadLanguage(langCode: String, langName: String) {
        statusText.text = "جاري بدء تنزيل ${langName}..."
        progressDownload.visibility = View.VISIBLE
        progressDownload.isIndeterminate = true

        val model = TranslateRemoteModel.Builder(langCode).build()
        val conditions = DownloadConditions.Builder().build()

        modelManager.download(model, conditions)
            .addOnSuccessListener {
                statusText.text = "✅ تم تنزيل ${langName} وهي جاهزة الآن"
                progressDownload.visibility = View.GONE
            }
            .addOnFailureListener { e ->
                statusText.text = "❌ خطأ في التنزيل: ${e.message}"
                progressDownload.visibility = View.GONE
            }
    }
}
