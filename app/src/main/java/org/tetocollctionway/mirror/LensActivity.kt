package org.tetocollctionway.mirror

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.tetocollctionway.mirror.databinding.ActivityLensBinding

class LensActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLensBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // العدسة جاهزة لاستقبال أوامر الكاميرا و ML Kit
    }
}
