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

        // الربط بالأسماء الجديدة حسب وصفك
        binding.btnTextTranslator.setOnClickListener {
            startActivity(Intent(this, TextTranslatorActivity::class.java))
        }
        
        binding.btnDialogTranslator.setOnClickListener {
            // سنضيف الكود عند بدء الكارت الثاني
        }

        binding.btnDocLens.setOnClickListener { startActivity(Intent(this, DocLensActivity::class.java))
            // سنضيف الكود عند بدء الكارت الثالث
        }
    }
}
