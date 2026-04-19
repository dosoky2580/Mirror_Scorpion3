package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewById<Button>(R.id.btnVoiceClone).setOnClickListener {
            Toast.makeText(this, "قريباً: شاشة تسجيل الـ 30 ثانية", Toast.LENGTH_SHORT).show()
        }
    }
}
