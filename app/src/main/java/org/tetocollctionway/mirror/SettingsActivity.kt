package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // إعداد قائمة الأصوات الـ 5
        val voices = arrayOf("سيف (ذكر)", "سلمى (أنثى)", "سما (أنثى)", "سارة (أنثى)", "صوتك الشخصي (مدفوع)")
        val spinner = findViewById<Spinner>(R.id.spinnerVoices)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, voices)
        spinner.adapter = adapter

        // ربط الأزرار بالوظائف
        findViewById<Button>(R.id.btnVoiceClone).setOnClickListener {
            startActivity(Intent(this, VoiceCloneActivity::class.java))
        }

        findViewById<Button>(R.id.btnLangs).setOnClickListener {
            startActivity(Intent(this, LanguageDownloadActivity::class.java))
        }

        findViewById<Button>(R.id.btnDarkMode).setOnClickListener {
            Toast.makeText(this, "سيتم الحفظ التلقائي للوضع المظلم", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnAbout).setOnClickListener {
            Toast.makeText(this, "Mirror Scorpion v1.0\nبواسطة: tetocollctionway", Toast.LENGTH_LONG).show()
        }
    }
}
