package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CreativeStoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creative_stories)

        val etStory = findViewById<EditText>(R.id.et_user_story)
        val btnGenerate = findViewById<Button>(R.id.btn_generate_video)

        btnGenerate.setOnClickListener {
            val story = etStory.text.toString().trim()
            
            if (story.isEmpty()) {
                Toast.makeText(this, "اكتب قصتك أولاً يا صديقي", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // تطبيق بروتوكول ميرور الصارم
            val violation = checkMirrorProtocol(story.lowercase())
            
            if (violation != null) {
                // إظهار سبب الرفض بناءً على نوع الانتهاك
                Toast.makeText(this, "تنبيه: ${violation}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "قصة رائعة! تتوافق مع معايير ميرور.. جاري التحويل لمشاهد.", Toast.LENGTH_SHORT).show()
                // هنا سيتم استدعاء محرك توليد الفيديو
            }
        }
    }

    private fun checkMirrorProtocol(text: String): String? {
        // 1. فحص العنف والكلمات البذيئة
        val violenceWords = listOf("قتل", "دم", "ضرب", "تعذيب", "انتقام عنيف")
        if (violenceWords.any { text.contains(it) }) return "بروتوكول ميرور يمنع مشاهد العنف."

        // 2. فحص الكراهية والتنمر
        val hateSpeech = listOf("أكره", "غبي", "قبيح", "فاشل") // أمثلة بسيطة للتنمر
        if (hateSpeech.any { text.contains(it) }) return "نحن نشجع الإبداع، لا التنمر أو خطاب الكراهية."

        // 3. فحص التلامس غير اللائق
        val contactWords = listOf("تلامس", "حضن", "قبلة")
        if (contactWords.any { text.contains(it) }) return "بروتوكول القيم يمنع مشاهد التلامس."

        return null // القصة سليمة
    }
}
