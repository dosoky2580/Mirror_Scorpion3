package org.tetocollctionway.mirror

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.nl.translate.TranslateLanguage
import java.io.InputStream

class DocumentTranslateActivity : AppCompatActivity() {

    private lateinit var txtFileName: TextView
    private lateinit var txtFinalResult: TextView
    private lateinit var btnTranslate: Button
    private var selectedFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_translate)

        txtFileName = findViewById(R.id.txtFileName)
        txtFinalResult = findViewById(R.id.txtFinalResult)
        btnTranslate = findViewById(R.id.btnTranslateDoc)

        findViewById<Button>(R.id.btnBrowse).setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "application/pdf"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            startActivityForResult(Intent.createChooser(intent, "اختر ملف"), 1001)
        }

        btnTranslate.setOnClickListener {
            selectedFileUri?.let { uri ->
                txtFinalResult.text = "جاري سحب الورقة..."
                processAndTranslate(uri)
            }
        }
    }

    private fun processAndTranslate(uri: Uri) {
        try {
            val iStream: InputStream? = contentResolver.openInputStream(uri)
            if (iStream != null) {
                val reader = PdfReader(iStream)
                val pdf = PdfDocument(reader)
                val pages = if (pdf.numberOfPages > 5) 5 else pdf.numberOfPages
                var rawText = ""
                for (i in 1..pages) {
                    rawText += PdfTextExtractor.getTextFromPage(pdf.getPage(i))
                }
                pdf.close()
                
                if (rawText.isNotEmpty()) {
                    translateText(rawText)
                } else {
                    txtFinalResult.text = "المستند فارغ"
                }
            }
        } catch (e: Exception) {
            txtFinalResult.text = "خطأ في قراءة الورقة"
        }
    }

    private fun translateText(text: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.ARABIC)
            .build()
        val translator = Translation.getClient(options)
        
        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                translator.translate(text)
                    .addOnSuccessListener { translated ->
                        txtFinalResult.text = translated
                    }
                    .addOnFailureListener {
                        txtFinalResult.text = "فشلت الترجمة"
                    }
            }
            .addOnFailureListener {
                txtFinalResult.text = "جاري تحميل محرك الترجمة..."
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            selectedFileUri = data?.data
            txtFileName.text = "تم التقاط الورقة"
            btnTranslate.visibility = View.VISIBLE
        }
    }
}
