package org.tetocollctionway.mirror

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor

class DocumentTranslateActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private val PICK_PDF_CODE = 105

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_translate)

        val btnPick = findViewById<Button>(R.id.btnPickFile)
        tvResult = findViewById(R.id.tvLensResult) // سنستخدم هذا المربع لعرض النص

        btnPick.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(intent, PICK_PDF_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PDF_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                extractTextFromPdf(uri)
            }
        }
    }

    private fun extractTextFromPdf(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val reader = PdfReader(inputStream)
            val pdfDoc = PdfDocument(reader)
            val n = pdfDoc.numberOfPages
            var extractedText = ""
            for (i in 1..n) {
                extractedText += PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i))
            }
            tvResult.text = if (extractedText.isEmpty()) "المستند فارغ أو محمي" else extractedText
            pdfDoc.close()
        } catch (e: Exception) {
            tvResult.text = "خطأ في قراءة الملف: ${e.message}"
        }
    }
}
