package org.tetocollctionway.mirror

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class VoiceCloneActivity : AppCompatActivity() {

    private lateinit var txtPrompt: TextView
    private lateinit var txtTimer: TextView
    private lateinit var btnRecord: Button
    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_clone)

        txtPrompt = findViewById(R.id.txtPrompt)
        txtTimer = findViewById(R.id.txtTimer)
        btnRecord = findViewById(R.id.btnRecord)

        // تحديد النص بناءً على لغة الجهاز
        setPromptByLanguage()

        btnRecord.setOnClickListener {
            if (!isRecording) {
                startRecordingSession()
            }
        }
    }

    private fun setPromptByLanguage() {
        val currentLang = Locale.getDefault().language
        txtPrompt.text = when (currentLang) {
            "ar" -> "إن المعرفة هي المصباح الذي ينير دروب البشرية، واللغة هي الجسر الذي يربط القلوب والعقول عبر الزمان والمكان. في مرآة هذا التطبيق، نسعى لتحطيم الحواجز وصناعة فجر جديد للتواصل الإنساني بصدق ووضوح."
            "en" -> "The quick brown fox jumps over the lazy dog. Swift gathering clouds disperse as bright sunshine illuminates the valley, creating a breathtaking view of nature's eternal beauty and complex harmony."
            "tr" -> "Pijamalı hasta, yağız şoföre çabucak güvendi. Hayatın her anı yeni bir öğrenme fırsatıdır ve dil, insanları birbirine bağlayan en güçlü köprüdür. Bu uygulamada engelleri aşmayı hedefliyoruz."
            else -> "Please read this text clearly for thirty seconds. Your voice is unique, and capturing its essence requires a variety of phonetic sounds to ensure the best possible cloning quality."
        }
    }

    private fun startRecordingSession() {
        isRecording = true
        btnRecord.isEnabled = false
        
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                txtTimer.text = "00:" + String.format("%02d", millisUntilFinished / 1000)
            }

            override fun onFinish() {
                txtTimer.text = "تم التسجيل!"
                isRecording = false
                btnRecord.isEnabled = true
                // هنا هنربط مع ElevenLabs في الخطوة الجاية
            }
        }.start()
    }
}
