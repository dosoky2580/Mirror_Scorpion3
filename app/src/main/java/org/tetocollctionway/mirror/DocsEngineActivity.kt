package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.tetocollctionway.mirror.databinding.ActivityDocsEngineBinding
import com.google.mlkit.nl.translate.TranslateLanguage

class DocsEngineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDocsEngineBinding
    private var selectedLang = TranslateLanguage.TURKISH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocsEngineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBrowse.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "application/pdf" }
            startActivityForResult(intent, 300)
        }

        binding.btnStartTranslate.setOnClickListener {
            // إضافة قائمة اختيار اللغة قبل البدء
            val langs = arrayOf("التركية", "الإنجليزية", "البنغالية", "الهندية")
            AlertDialog.Builder(this)
                .setTitle("اختر لغة ترجمة المستند")
                .setItems(langs) { _, which ->
                    binding.progressBar.visibility = View.VISIBLE
                    Toast.makeText(this, "جاري استخراج النص وترجمته إلى ${langs[which]}...", Toast.LENGTH_LONG).show()
                    // سيتم ربط PDFBox هنا في الخطوة التالية بعد استقرار الـ Build
                }.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            binding.tvFileName.text = "الملف جاهز: " + (data.data?.path?.split("/")?.last() ?: "document.pdf")
            binding.btnStartTranslate.visibility = View.VISIBLE
        }
    }
}
