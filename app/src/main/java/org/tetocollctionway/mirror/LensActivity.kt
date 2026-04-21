package org.tetocollctionway.mirror

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import org.tetocollctionway.mirror.databinding.ActivityLensBinding

class LensActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLensBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // هنا هنربط الكاميرا ونعرض قائمة اللغات المنسدلة في الركن اليمين
        // زي ما طلبت بالظبط لتبديل اللغة
    }
}
