package org.tetocollctionway.mirror
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.tetocollctionway.mirror.databinding.ActivityDocLensBinding

class DocLensActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDocLensBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocLensBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.btnLensFeature.setOnClickListener {
            startActivity(Intent(this, LensActivity::class.java))
        }
        binding.btnDocsFeature.setOnClickListener {
            startActivity(Intent(this, DocsEngineActivity::class.java))
        }
    }
}
