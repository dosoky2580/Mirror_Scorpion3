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
            val story = etStory.text.toString().lowercase()
            
            // تطبيق بروتوكول ميرور (فلتر الكلمات والمشاهد)
            if (containsViolations(story)) {
                Toast.makeText(this, "عذراً، القصة تخالف بروتوكول قيم ميرور سكربيون (عنف أو كلمات غير لائقة)", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "جاري معالجة القصة وتوليد المشاهد المتوافقة...", Toast.LENGTH_SHORT).show()
                // هنا سيتم استدعاء محرك Veo لتوليد الفيديو لاحقاً
            }
        }
    }

    private fun containsViolations(text: String): Boolean {
        // قائمة مبدئية للكلمات المرفوضة حسب بروتوكولك
        val forbiddenWords = listOf("ضرب", "قتل", "دم", "شتيمة", "عنف") 
        return forbiddenWords.any { text.contains(it) }
    }
}
