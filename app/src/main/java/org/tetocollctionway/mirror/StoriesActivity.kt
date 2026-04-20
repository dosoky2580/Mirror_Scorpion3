package org.tetocollctionway.mirror

import android.content.Context
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

        // جلب اسم المستخدم الذي سجله في البداية
        val sharedPref = getSharedPreferences("MirrorPrefs", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("userName", "صديقي")

        val tvDisplay = findViewById<TextView>(R.id.tv_content_display)
        val btnHadith = findViewById<Button>(R.id.btn_hadith)
        val switchInspiration = findViewById<Switch>(R.id.switch_inspiration)
        val btnAiMessage = findViewById<Button>(R.id.btn_ai_message)

        btnHadith.setOnClickListener {
            tvDisplay.text = hadithList.random()
        }

        switchInspiration.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "يا $userName، سيقوم ميرور بإرسال رسائل ملهمة لك كل 3 ساعات", Toast.LENGTH_LONG).show()
            }
        }

        btnAiMessage.setOnClickListener {
            // هنا تظهر قوة فكرتك في التخصيص
            val messages = listOf(
                "يا $userName، إن مع العسر يسراً.. تذكر دائماً أنك أقوى مما تظن.",
                "لا تقلق يا $userName، فكل انكسار هو بداية لنجاح أعظم.",
                "الوقت هو عملتك يا $userName، استثمر ثوانيك في صنع حلمك."
            )
            tvDisplay.text = messages.random()
        }
    }
}
