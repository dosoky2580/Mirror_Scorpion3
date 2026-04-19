package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // تعريف الكروت
        val cardTranslate = findViewById<CardView>(R.id.cardTranslate)
        val cardDialogue = findViewById<CardView>(R.id.cardDialogue)
        val cardDocs = findViewById<CardView>(R.id.cardDocs)
        val cardStories = findViewById<CardView>(R.id.cardStories)

        cardTranslate.setOnClickListener {
            Toast.makeText(this, "قريباً: شاشة الترجمة النصية", Toast.LENGTH_SHORT).show()
        }

        cardDialogue.setOnClickListener {
            Toast.makeText(this, "قريباً: وضع الحوار", Toast.LENGTH_SHORT).show()
        }
    }
}
