package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class StoryTheaterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_theater)

        val storyText = intent.getStringExtra("STORY_TEXT")
        Toast.makeText(this, "بدء عرض القصة: ${storyText?.take(20)}...", Toast.LENGTH_LONG).show()

        // منطق تشغيل المؤثرات الصوتية (SFX)
        findViewById<ImageButton>(R.id.btn_sfx_wind).setOnClickListener {
            Toast.makeText(this, "تشغيل مؤثر الرياح الهادئة", Toast.LENGTH_SHORT).show()
        }
    }
}
