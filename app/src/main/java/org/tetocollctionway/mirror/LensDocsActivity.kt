package org.tetocollctionway.mirror

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LensDocsActivity : AppCompatActivity() {

    private lateinit var layoutOriginal: FrameLayout
    private lateinit var layoutTranslated: FrameLayout
    private lateinit var tvStatus: TextView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lens_docs_v2) // تحديث الواجهة

        // منطق ترجمة المستندات والضغطة الطويلة
        val btnTranslate = findViewById<Button>(R.id.btn_start_translation)
        layoutOriginal = findViewById(R.id.layout_original_doc)
        layoutTranslated = findViewById(R.id.layout_translated_doc)
        tvStatus = findViewById(R.id.tv_doc_status)

        btnTranslate.setOnClickListener {
            // محاكاة دائرة التحميل (3 ثواني) ثم ظهور الورقة المترجمة
            tvStatus.text = "جاري التحميل..."
            btnTranslate.visibility = View.GONE
            
            it.postDelayed({
                tvStatus.text = "تمت الترجمة. (اضغط مطولاً للمقارنة)"
                layoutTranslated.visibility = View.VISIBLE
                // تأثير سحب الورقة من اليمين (Animation بسيط)
                layoutTranslated.translationX = 1000f
                layoutTranslated.animate().translationX(0f).setDuration(500).start()
            }, 3000)
        }

        // سحر ميرور: ضغطة طويلة للأصل، رفع الإصبع للمترجم
        layoutTranslated.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    layoutTranslated.alpha = 0f // إخفاء المترجم لإظهار الأصلي تحتـه
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    layoutTranslated.alpha = 1f // إعادة المترجم
                    true
                }
                else -> false
            }
        }
    }
}
