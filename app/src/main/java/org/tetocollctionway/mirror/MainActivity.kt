package org.tetocollctionway.mirror

import android.os.Bundle
import android.app.Activity
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.text = "Mirror Scorpion 2026 - Heart of Adham"
        setContentView(textView)
    }
}
