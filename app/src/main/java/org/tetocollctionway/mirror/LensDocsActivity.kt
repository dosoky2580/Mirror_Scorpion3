package org.tetocollctionway.mirror

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LensDocsActivity : AppCompatActivity() {

    private lateinit var viewFinder: PreviewView
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var tvResult: TextView
    private var targetLang = TranslateLanguage.ARABIC

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lens_camera)

        viewFinder = findViewById(R.id.viewFinder)
        tvResult = findViewById(R.id.tv_lens_result)
        val spinner = findViewById<Spinner>(R.id.spinner_lens_lang)

        // إعداد قائمة اللغات (العدسة)
        val langs = arrayOf("العربية", "التركية", "الإنجليزية")
        val codes = arrayOf(TranslateLanguage.ARABIC, TranslateLanguage.TURKISH, TranslateLanguage.ENGLISH)
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, langs)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                targetLang = codes[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        if (allPermissionsGranted()) { startCamera() } 
        else { ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 10) }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also { it.setSurfaceProvider(viewFinder.surfaceProvider) }
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also { it.setAnalyzer(cameraExecutor) { proxy -> processImageProxy(proxy) } }

            cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageAnalyzer)
        }, ContextCompat.getMainExecutor(this))
    }

    @ExperimentalGetImage
    private fun processImageProxy(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: return
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                if (visionText.text.isNotEmpty()) {
                    translateText(visionText.text)
                }
            }
            .addOnCompleteListener { imageProxy.close() }
    }

    private fun translateText(text: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH) // افتراضياً من الإنجليزية
            .setTargetLanguage(targetLang)
            .build()
        val translator = Translation.getClient(options)

        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(text).addOnSuccessListener { translatedText ->
                runOnUiThread {
                    tvResult.visibility = View.VISIBLE
                    tvResult.text = translatedText
                }
            }
        }
    }

    private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(baseContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    override fun onDestroy() { super.onDestroy(); cameraExecutor.shutdown() }
}
