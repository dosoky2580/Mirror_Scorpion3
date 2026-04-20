package org.tetocollctionway.mirror

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val sharedPref = getSharedPreferences("MirrorPrefs", Context.MODE_PRIVATE)
        if (sharedPref.contains("userName")) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_welcome)

        val etName = findViewById<EditText>(R.id.et_user_name)
        val btnNext = findViewById<Button>(R.id.btn_next)

        btnNext.setOnClickListener {
            val name = etName.text.toString().trim()
            if (name.isNotEmpty()) {
                sharedPref.edit().putString("userName", name).apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "من فضلك أخبر ميرور باسمك", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
