package org.tetocollctionway.mirror

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CreativeStoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creative_stories)

        val cbAgree = findViewById<CheckBox>(R.id.cb_agree_protocol)
        val etStory = findViewById<EditText>(R.id.et_user_story)
        val btnGenerate = findViewById<Button>(R.id.btn_generate_video)

        // تفعيل الكتابة والزر فقط عند الموافقة
        cbAgree.setOnCheckedChangeListener { _, isChecked ->
            etStory.isEnabled = isChecked
            btnGenerate.isEnabled = isChecked
            if (isChecked) etStory.hint = "اطلق لخيالك العنان..."
        }

        btnGenerate.setOnClickListener {
            val story = etStory.text.toString().trim().lowercase()
            
            // تطبيق بروتوكول ميرور الصارم
            val violation = validateProtocol(story)
            
            if (violation != null) {
                // الرفض مع ذكر السبب
                showProtocolError(violation)
            } else {
                // القبول والتوليد
                Toast.makeText(this, "تم قبول القصة.. جاري إرسالها لمحرك Veo لتوليد الفيديو.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validateProtocol(text: String): String? {
        if (text.isEmpty()) return "برجاء كتابة نص أولاً."
        
        // فحص العنف والكراهية والتنمر (بناءً على بروتوكول تامر)
        val forbidden = mapOf(
            "عنف" to listOf("قتل", "دم", "ضرب", "تعذيب", "سلاح"),
            "تلامس" to listOf("حضن", "قبلة", "تلامس", "إغراء"),
            "كراهية وتنمر" to listOf("أكره", "غبي", "فاشل", "قبيح", "حثالة")
        )

        for ((category, words) in forbidden) {
            if (words.any { text.contains(it) }) {
                return "مرفوض: النص يحتوي على إيحاءات ($category) تتعارض مع بروتوكول ميرور."
            }
        }
        return null
    }

    private fun showProtocolError(error: String) {
        android.app.AlertDialog.Builder(this)
            .setTitle("تعارض مع البروتوكول")
            .setMessage(error)
            .setPositiveButton("فهمت") { d, _ -> d.dismiss() }
            .show()
    }
}
