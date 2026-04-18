package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // بما أننا استخدمنا GridLayout في الـ XML، الكروت هي عبارة عن أزرار (Buttons)
        // الكارت الأول هو أول زر في الجريد
        val btnTextTranslation = findViewById<Button>(R.id.btnTextTranslation)
        val btnConversation = findViewById<Button>(R.id.btnConversation)
        btnConversation.setOnClickListener { startActivity(Intent(this, ConversationActivity::class.java)) }
        val btnSettings = findViewById<Button>(R.id.btnSettings)
        btnSettings.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }
        btnTextTranslation.setOnClickListener {
            val intent = Intent(this, TranslationActivity::class.java)
            startActivity(intent)
        }
    }
}
