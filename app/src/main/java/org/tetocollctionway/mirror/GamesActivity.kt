package org.tetocollctionway.mirror
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.tetocollctionway.mirror.databinding.ActivityGamesBinding

class GamesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGamesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.btnChess3d.setOnClickListener {
            Toast.makeText(this, "جاري تحميل محرك الشطرنج 3D...", Toast.LENGTH_SHORT).show()
        }
        
        binding.btnRubiks3d.setOnClickListener {
            Toast.makeText(this, "جاري فتح مكعب روبيك مع دليل الحلول...", Toast.LENGTH_SHORT).show()
        }
    }
}
