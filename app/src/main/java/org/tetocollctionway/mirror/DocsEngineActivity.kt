package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.tetocollctionway.mirror.databinding.ActivityDocsEngineBinding

class DocsEngineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDocsEngineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocsEngineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // زر استعراض الملفات
        binding.btnBrowse.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(intent, 300)
        }

        // زر بدء الترجمة (الفعل الحقيقي)
        binding.btnStartTranslate.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            Toast.makeText(this, "جاري استخراج النصوص وترجمتها...", Toast.LENGTH_LONG).show()
            // هنا سنضيف محرك PDFBox أو Google Scan لاحقاً
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 300 && resultCode == RESULT_OK) {
            binding.tvFileName.text = "تم اختيار الملف: " + data?.data?.path?.split("/")?.last()
            binding.btnStartTranslate.visibility = View.VISIBLE
        }
    }
}
