package org.tetocollctionway.mirror

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DocumentTranslateActivity : AppCompatActivity() {

    private lateinit var txtFileName: TextView
    private lateinit var btnTranslate: Button
    private var selectedFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_translate)

        val btnBrowse = findViewById<Button>(R.id.btnBrowse)
        btnTranslate = findViewById(R.id.btnTranslateDoc)
        txtFileName = findViewById(R.id.txtFileName)

        btnBrowse.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(Intent.createChooser(intent, "اختر ملف PDF"), 1001)
        }

        btnTranslate.setOnClickListener {
            if (selectedFileUri != null) {
                Toast.makeText(this, "جاري قراءة الملف وترجمته (أول 5 صفحات)...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                selectedFileUri = uri
                txtFileName.text = "تم اختيار الملف بنجاح"
                btnTranslate.visibility = View.VISIBLE
            }
        }
    }
}
