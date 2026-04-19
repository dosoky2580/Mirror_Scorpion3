package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // الربط مع الكارت الجديد بدلاً من الزر القديم
        val cardSettings = findViewById<CardView>(R.id.cardSettings)
        cardSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
