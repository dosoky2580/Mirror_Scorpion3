package org.tetocollctionway.mirror
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.tetocollctionway.mirror.databinding.ActivityHadithViewerBinding

class HadithViewerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHadithViewerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHadithViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // هنا سنضع قائمة الأحاديث لاحقاً
    }
}
