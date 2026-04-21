package org.tetocollctionway.mirror
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.tetocollctionway.mirror.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.btnUpgrade.setOnClickListener {
            Toast.makeText(this, "قريباً.. النسخة المدفوعة بمميزات لا محدودة", Toast.LENGTH_LONG).show()
        }
    }
}
