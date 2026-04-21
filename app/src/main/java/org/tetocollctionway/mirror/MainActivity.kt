package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.tetocollctionway.mirror.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // الربط مع الكروت الجديدة بناءً على الـ IDs في XML
        binding.cardText.setOnClickListener {
            startActivity(Intent(this, TextTranslatorActivity::class.java))
        }

        binding.cardDialog.setOnClickListener {
            // سننشئ الحوار المترجم لاحقاً
        }

        binding.cardDocsLens.setOnClickListener {
            // سننشئ المستندات والعدسة لاحقاً
        }
        
        // باقي الكروت تضاف هنا بنفس الطريقة
    }
}
