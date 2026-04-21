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

        // 1. الترجمة الفورية (الآن تفتح الشاشة الصحيحة)
        binding.cardTextTranslator.setOnClickListener {
            startActivity(Intent(this, TextTranslatorActivity::class.java))
        }

        // 2. العدسة (الآن تفتح شاشة العدسة)
        binding.cardLens.setOnClickListener {
            startActivity(Intent(this, LensActivity::class.java))
        }

        // 3. المستندات (الآن تفتح محرك المستندات)
        binding.cardDocs.setOnClickListener {
            startActivity(Intent(this, DocsEngineActivity::class.java))
        }

        // باقي الأزرار (سنقوم بتفعيلها لاحقاً)
    }
}
