package org.tetocollctionway.mirror

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.tetocollctionway.mirror.databinding.ActivityStoriesInspirationBinding

class StoriesInspirationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoriesInspirationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoriesInspirationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHadith.setOnClickListener {
            // فتح شاشة الأحاديث (سنقوم بتسجيلها في المانيفست بالأسفل)
            startActivity(Intent(this, HadithViewerActivity::class.java))
        }

        binding.btnInspiration.setOnClickListener {
            Toast.makeText(this, "أداة الذكاء الاصطناعي تحلل حالتك الآن لتبعث بالإلهام...", Toast.LENGTH_LONG).show()
        }

        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "مفعلة كل 3 ساعات" else "معطلة"
            Toast.makeText(this, "رسائل الإلهام الإشعارية: $status", Toast.LENGTH_SHORT).show()
        }
    }
}
