package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class GamesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        findViewById<CardView>(R.id.card_chess).setOnClickListener {
            Toast.makeText(this, "جاري تحميل محرك الشطرنج 3D...", Toast.LENGTH_SHORT).show()
        }

        findViewById<CardView>(R.id.card_rubiks).setOnClickListener {
            Toast.makeText(this, "جاري تحضير مكعب روبيك...", Toast.LENGTH_SHORT).show()
        }
    }
}
