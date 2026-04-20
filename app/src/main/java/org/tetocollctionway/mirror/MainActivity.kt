package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. كارت الترجمة النصية - الربط الحقيقي
        findViewById<View>(R.id.card_text_trans).setOnClickListener {
            val intent = Intent(this, TextTranslatorActivity::class.java)
            startActivity(intent)
        }

        // باقي الكروت مؤقتاً بدون ربط فعلي حتى ننشئ شاشاتها
        findViewById<View>(R.id.card_dialog_trans).setOnClickListener { }
            val intent = Intent(this, DialogTranslatorActivity::class.java)
            startActivity(intent)
        findViewById<View>(R.id.card_lens_docs).setOnClickListener { }
            val intent = Intent(this, LensDocsActivity::class.java)
            startActivity(intent)
        findViewById<View>(R.id.card_stories).setOnClickListener { }
        findViewById<View>(R.id.card_games).setOnClickListener { }
        findViewById<View>(R.id.card_settings).setOnClickListener { }
    }
}
