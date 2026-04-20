package org.tetocollctionway.mirror

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ربط الكروت بالأسماء الصحيحة الموجودة في ملف الـ XML
        
        // 1. كارت الترجمة النصية
        findViewById<View>(R.id.card_text_trans).setOnClickListener {
            showToast("جاري تشغيل محرك الترجمة النصية...")
        }

        // 2. كارت حوار مترجم
        findViewById<View>(R.id.card_dialog_trans).setOnClickListener {
            showToast("جاري تحضير الحوار المزدوج...")
        }

        // 3. كارت العدسة والمستندات
        findViewById<View>(R.id.card_lens_docs).setOnClickListener {
            showToast("فتح العدسة والمستندات...")
        }

        // 4. كارت القصص والإلهام
        findViewById<View>(R.id.card_stories).setOnClickListener {
            showToast("ركن القصص وروح التطبيق...")
        }

        // 5. كارت الألعاب
        findViewById<View>(R.id.card_games).setOnClickListener {
            showToast("منطقة ألعاب الذكاء 3D...")
        }

        // 6. كارت الإعدادات
        findViewById<View>(R.id.card_settings).setOnClickListener {
            showToast("فتح الإعدادات والترقية...")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
