package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ربط كارت الترجمة النصية (الكارت الأول)
        findViewById<CardView>(R.id.cardTextTranslate).setOnClickListener {
            startActivity(Intent(this, TextTranslateActivity::class.java))
        }
        
        // ربط كارت حوار مترجم (الكارت الثاني)
        findViewById<CardView>(R.id.cardChatTranslate).setOnClickListener {
            startActivity(Intent(this, ChatTranslateActivity::class.java))
        }

        // ربط كارت مستندات وعدسة (الكارت الثالث)
        findViewById<CardView>(R.id.cardDocLens).setOnClickListener {
            startActivity(Intent(this, DocumentTranslateActivity::class.java))
        }

        // باقي الكروت (الإعدادات، القصص، الألعاب) سيتم ربطها عند برمجة شاشاتها
    }
}
