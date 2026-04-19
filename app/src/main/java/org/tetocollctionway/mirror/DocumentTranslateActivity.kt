package org.tetocollctionway.mirror

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DocumentTranslateActivity : AppCompatActivity() {

    private lateinit var layoutPdf: LinearLayout
    private lateinit var layoutLens: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_translate)

        layoutPdf = findViewById(R.id.layoutPdf)
        layoutLens = findViewById(R.id.layoutLens)
        val btnPdf = findViewById<Button>(R.id.btnTabPdf)
        val btnLens = findViewById<Button>(R.id.btnTabLens)

        btnPdf.setOnClickListener {
            layoutPdf.visibility = View.VISIBLE
            layoutLens.visibility = View.GONE
            btnPdf.backgroundTintList = getColorStateList(R.color.teal_200) // سنستخدم اللون الأساسي للتطبيق
            btnLens.backgroundTintList = getColorStateList(android.R.color.darker_gray)
        }

        btnLens.setOnClickListener {
            layoutPdf.visibility = View.GONE
            layoutLens.visibility = View.VISIBLE
            btnLens.backgroundTintList = getColorStateList(R.color.teal_200)
            btnPdf.backgroundTintList = getColorStateList(android.R.color.darker_gray)
        }
        
        // سيتم ربط محرك iText ومحرك الكاميرا في الخطوة القادمة بعد نجاح البناء
    }
}
