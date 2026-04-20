package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stories)

        findViewById<ImageButton>(R.id.btn_effect_wind).setOnClickListener {
            Toast.makeText(this, "تشغيل صوت الرياح المحاكي...", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.btn_effect_sea).setOnClickListener {
            Toast.makeText(this, "تشغيل صوت البحر...", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.btn_effect_horses).setOnClickListener {
            Toast.makeText(this, "تشغيل صوت سنابك الخيل...", Toast.LENGTH_SHORT).show()
        }
    }
}
