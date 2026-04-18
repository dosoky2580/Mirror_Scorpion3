package org.tetocollctionway.mirror

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import android.view.Gravity

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.text = "Mirror Scorpion 2026\nAI Engine: Ready\nHeart: Adham"
        textView.textSize = 24f
        textView.gravity = Gravity.CENTER
        setContentView(textView)
    }
}
