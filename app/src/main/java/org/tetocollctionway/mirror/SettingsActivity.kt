package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val btnDownload = findViewById<Button>(R.id.btnDownloadOffline)
        btnDownload.setOnClickListener {
            // هنا تظهر رسالة النسخة المدفوعة أو يبدأ التحميل لو متاح
            Toast.makeText(this, "قريباً: تحميل 100 لغة للعمل أوفلاين (النسخة المدفوعة)", Toast.LENGTH_LONG).show()
        }
    }
}
