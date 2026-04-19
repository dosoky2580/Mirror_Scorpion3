package org.tetocollctionway.mirror

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File
import java.util.Locale

class VoiceCloneActivity : AppCompatActivity() {

    private lateinit var txtPrompt: TextView
    private lateinit var txtTimer: TextView
    private lateinit var btnRecord: Button
    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null
    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_clone)

        txtPrompt = findViewById(R.id.txtPrompt)
        txtTimer = findViewById(R.id.txtTimer)
        btnRecord = findViewById(R.id.btnRecord)

        setPromptByLanguage()

        btnRecord.setOnClickListener {
            if (!isRecording) {
                checkPermissionsAndStart()
            }
        }
    }

    private fun checkPermissionsAndStart() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 200)
        } else {
            startRecording()
        }
    }

    private fun startRecording() {
        try {
            audioFile = File(externalCacheDir, "user_voice_sample.mp3")
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(audioFile?.absolutePath)
                prepare()
                start()
            }

            isRecording = true
            btnRecord.isEnabled = false
            btnRecord.alpha = 0.5f

            object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    txtTimer.text = "00:" + String.format("%02d", millisUntilFinished / 1000)
                }

                override fun onFinish() {
                    stopRecording()
                }
            }.start()
        } catch (e: Exception) {
            Toast.makeText(this, "خطأ في بدء التسجيل: ${e.message}", Toast.LENGTH_SHORT).show()
            btnRecord.isEnabled = true
            btnRecord.alpha = 1.0f
        }
    }

    private fun stopRecording() {
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
            isRecording = false
            btnRecord.isEnabled = true
            btnRecord.alpha = 1.0f
            txtTimer.text = "تم الحفظ!"
            Toast.makeText(this, "تم تسجيل بصمة صوتك بنجاح", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            isRecording = false
            btnRecord.isEnabled = true
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
}
