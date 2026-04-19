package org.tetocollctionway.mirror

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.nl.translate.TranslateLanguage

class ImageTranslateActivity : AppCompatActivity() {

    private lateinit var imgSelected: ImageView
    private lateinit var txtResult: TextView
    private lateinit var btnProcess: Button
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_translate)

        imgSelected = findViewById(R.id.imgSelected)
        txtResult = findViewById(R.id.txtImgResult)
        btnProcess = findViewById(R.id.btnProcessImg)

        findViewById<Button>(R.id.btnSelectImg).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 2001)
        }

        btnProcess.setOnClickListener {
            imageUri?.let { uri ->
                processImage(uri)
            }
        }
    }

    private fun processImage(uri: Uri) {
        try {
            val image = InputImage.fromFilePath(this, uri)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            
            txtResult.text = "جاري مسح الصورة..."
            
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    if (visionText.text.isNotEmpty()) {
                        translateImageText(visionText.text)
                    } else {
                        txtResult.text = "لم يتم العثور على نص"
                    }
                }
                .addOnFailureListener {
                    txtResult.text = "فشل في التعرف على النص"
                }
        } catch (e: Exception) {
            txtResult.text = "خطأ في تحميل الصورة"
        }
    }

    private fun translateImageText(text: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.ARABIC)
            .build()
        val translator = Translation.getClient(options)

        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                translator.translate(text)
                    .addOnSuccessListener { translated ->
                        txtResult.text = translated
                    }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2001 && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            imgSelected.setImageURI(imageUri)
            btnProcess.visibility = View.VISIBLE
        }
    }
}
