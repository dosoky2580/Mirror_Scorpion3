package org.tetocollctionway.mirror

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*

class TextTranslatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translator)

        val btnSelectLang = findViewById<Button>(R.id.btn_select_language)
        val etInput = findViewById<EditText>(R.id.et_input_text)
        val tvOutput = findViewById<TextView>(R.id.tv_translated_text)
        val btnMic = findViewById<ImageButton>(R.id.btn_mic)

        // سنقوم ببرمجة منطق الترجمة والـ 100 لغة في الخطوة القادمة
        btnSelectLang.setOnClickListener {
            Toast.makeText(this, "قائمة الـ 100 لغة قيد التحضير...", Toast.LENGTH_SHORT).show()
        }
    }
}
