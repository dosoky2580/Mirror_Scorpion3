package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateRemoteModel

class LanguageDownloadActivity : AppCompatActivity() {

    private val modelManager = RemoteModelManager.getInstance()
    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_download)

        statusText = findViewById(R.id.statusText)
        val container = findViewById<LinearLayout>(R.id.langContainer)

        // القائمة اللي طلبتها
        val languages = mapOf(
            "البنغالية" to "bn",
            "الهندية" to "hi",
            "السيرلانكية (السينهالية)" to "si",
            "التركية" to "tr",
            "العربية" to "ar"
        )

        languages.forEach { (name, code) ->
            val btn = Button(this).apply {
                text = "تحميل ${name}"
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
        statusText.text = "جاري تحميل ${langName}..."
        val model = TranslateRemoteModel.Builder(langCode).build()
        val conditions = DownloadConditions.Builder().requireWifi().build()

        modelManager.download(model, conditions)
            .addOnSuccessListener {
                statusText.text = "تم تحميل ${langName} بنجاح (جاهز للعمل بدون إنترنت)"
                Toast.makeText(this, "تم التحميل!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                statusText.text = "فشل تحميل ${langName}"
            }
    }
}
