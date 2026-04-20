package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class StoryTheaterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_theater)

        val storyText = intent.getStringExtra("STORY_TEXT")
        if (storyText != null) {
            Toast.makeText(this, "بدء عرض القصة: ${storyText.take(20)}...", Toast.LENGTH_LONG).show()
        }

        // أزرار المؤثرات الصوتية (SFX)
        findViewById<ImageButton>(R.id.btn_sfx_wind).setOnClickListener {
            Toast.makeText(this, "تشغيل مؤثر: رياح الصحراء", Toast.LENGTH_SHORT).show()
        }
        findViewById<ImageButton>(R.id.btn_sfx_rain).setOnClickListener {
            Toast.makeText(this, "تشغيل مؤثر: صوت المطر", Toast.LENGTH_SHORT).show()
        }
        findViewById<ImageButton>(R.id.btn_sfx_nature).setOnClickListener {
            Toast.makeText(this, "تشغيل مؤثر: الطبيعة والطيور", Toast.LENGTH_SHORT).show()
        }
    }
}
