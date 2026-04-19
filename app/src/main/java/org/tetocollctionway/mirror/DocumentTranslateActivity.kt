package org.tetocollctionway.mirror

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DocumentTranslateActivity : AppCompatActivity() {

    private lateinit var edtUrlOrPath: EditText
    private lateinit var btnTranslateDoc: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_translate)

        edtUrlOrPath = findViewById(R.id.edtUrlOrPath)
        btnTranslateDoc = findViewById(R.id.btnTranslateDoc)

        findViewById<Button>(R.id.btnBrowse).setOnClickListener {
            // فتح مستعرض الملفات لاختيار PDF
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(intent, 100)
        }

        btnTranslateDoc.setOnClickListener {
            val path = edtUrlOrPath.text.toString()
            if (path.isNotEmpty()) {
                Toast.makeText(this, "جاري تحضير المستند للترجمة الفورية...", Toast.LENGTH_SHORT).show()
                // هنا سنفتح شاشة العرض الكامل (Full Screen) في الخطوة القادمة
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                edtUrlOrPath.setText(uri.path) // ملء المستطيل تلقائياً
                btnTranslateDoc.visibility = Button.VISIBLE // إظهار زر الترجمة
            }
        }
    }
}
