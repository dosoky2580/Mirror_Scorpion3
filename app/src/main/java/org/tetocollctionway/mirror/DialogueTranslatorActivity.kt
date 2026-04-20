package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DialogueTranslatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // تصميم مؤقت لضمان نجاح البناء وتجنب Unresolved reference
        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        val tv = TextView(this).apply { 
            text = "كارت الحوار المترجم\n(جاري ربط الأزرار اليمين واليسار حسب وصفك)"
            textSize = 20f
        }
        layout.addView(tv)
        setContentView(layout)
    }
}
