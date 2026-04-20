package org.tetocollctionway.mirror

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class StoriesActivity : AppCompatActivity() {

    private val hadithList = listOf(
        "يا عبادي إني حرمت الظلم على نفسي وجعلته بينكم محرماً فلا تظالموا.",
        "أنا عند ظن عبدي بي، وأنا معه إذا ذكرني.",
        "لو أنكم توكلون على الله حق توكله لرزقكم كما يرزق الطير."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stories_v2)

        val tvDisplay = findViewById<TextView>(R.id.tv_content_display)
        val btnHadith = findViewById<Button>(R.id.btn_hadith)
        val switchInspiration = findViewById<Switch>(R.id.switch_inspiration)

        btnHadith.setOnClickListener {
            // اختيار عشوائي كما طلبت
            tvDisplay.text = hadithList.random()
        }

        switchInspiration.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "سيقوم ميرور بإرسال رسائل ملهمة كل 3 ساعات", Toast.LENGTH_LONG).show()
            }
        }

        findViewById<Button>(R.id.btn_ai_message).setOnClickListener {
            tvDisplay.text = "رسالة من ميرور: إن مع العسر يسراً، لا تقلق يا تامر، قصتك لم تنتهِ بعد."
        }
    }
}
