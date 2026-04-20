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

        // الكروت المفعلة
        binding.btnTextTranslator.setOnClickListener {
            startActivity(Intent(this, TextTranslatorActivity::class.java))
        }
        
        // الكروت التي سنقوم ببرمجتها (جاهزة للربط)
        binding.btnDialogTranslator.setOnClickListener { /* سنضع الكود هنا لاحقاً */ }
        binding.btnDocTranslator.setOnClickListener { /* سنضع الكود هنا لاحقاً */ }
    }
}
