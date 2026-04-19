package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<CardView>(R.id.cardTextTranslate).setOnClickListener {
            startActivity(Intent(this, TextTranslateActivity::class.java))
        }
        
        findViewById<CardView>(R.id.cardDocLens).setOnClickListener {
            startActivity(Intent(this, DocumentTranslateActivity::class.java))
        }

        // الكروت الباقية جاهزة للربط فور برمجة شاشاتها
        val cardIds = listOf(R.id.cardChatTranslate, R.id.cardStories, R.id.cardGames, R.id.cardSettings)
        cardIds.forEach { id ->
            findViewById<CardView>(id).setOnClickListener {
                // Toast.makeText(this, "قريباً في ميرور", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
