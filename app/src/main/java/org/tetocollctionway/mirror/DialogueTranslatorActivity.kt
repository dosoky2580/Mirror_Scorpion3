package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DialogueTranslatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // سنستخدم حالياً تصميم مؤقت لضمان عدم الكراش حتى ننهي التصميم الاحترافي
        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        val tv = TextView(this).apply { text = "كارت الحوار المترجم - جاري العمل على التصميم" }
        layout.addView(tv)
        setContentView(layout)
    }
}
