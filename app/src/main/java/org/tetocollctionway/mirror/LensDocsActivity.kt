package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class LensDocsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lens_docs)

        findViewById<CardView>(R.id.btn_open_camera).setOnClickListener {
            Toast.makeText(this, "جاري تشغيل عدسة ميرور...", Toast.LENGTH_SHORT).show()
            // سيتم ربط CameraX أو ML Kit Vision هنا
        }

        findViewById<CardView>(R.id.btn_open_pdf).setOnClickListener {
            Toast.makeText(this, "اختر ملف PDF (حد 50 كلمة للنسخة المجانية)", Toast.LENGTH_SHORT).show()
        }
    }
}
