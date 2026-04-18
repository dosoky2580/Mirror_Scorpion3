package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // كارت الترجمة النصية
        findViewById<Button>(R.id.btnTextTranslation).setOnClickListener {
            startActivity(Intent(this, TranslationActivity::class.java))
        }

        // كارت حوار مترجم
        findViewById<Button>(R.id.btnConversation).setOnClickListener {
            startActivity(Intent(this, ConversationActivity::class.java))
        }

        // كارت الإعدادات
        findViewById<Button>(R.id.btnSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
