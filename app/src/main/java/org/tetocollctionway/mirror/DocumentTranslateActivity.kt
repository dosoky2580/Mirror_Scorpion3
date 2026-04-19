package org.tetocollctionway.mirror

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DocumentTranslateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_translate)

        val btnBrowse = findViewById<Button>(R.id.btnBrowse)
        val btnTranslate = findViewById<Button>(R.id.btnTranslateDoc)
        val txtFileName = findViewById<TextView>(R.id.txtFileName)

        btnBrowse.setOnClickListener {
            // هنا سنضيف كود اختيار الملف في الخطوة القادمة
            txtFileName.text = "تم اختيار الملف بنجاح"
            btnTranslate.visibility = View.VISIBLE
        }
    }
}
