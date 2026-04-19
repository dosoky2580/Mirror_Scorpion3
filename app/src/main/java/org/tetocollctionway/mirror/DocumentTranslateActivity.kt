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
import java.io.InputStream

class DocumentTranslateActivity : AppCompatActivity() {

    private lateinit var txtFileName: TextView
    private lateinit var btnTranslate: Button
    private var selectedFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_translate)

        txtFileName = findViewById(R.id.txtFileName)
        btnTranslate = findViewById(R.id.btnTranslateDoc)

        findViewById<Button>(R.id.btnBrowse).setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "application/pdf"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            startActivityForResult(Intent.createChooser(intent, "اختر ملف PDF"), 1001)
        }

        btnTranslate.setOnClickListener {
            selectedFileUri?.let { uri ->
                processAndTranslate(uri)
            }
        }
    }

    private fun processAndTranslate(uri: Uri) {
        try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            if (inputStream != null) {
                val pdfReader = PdfReader(inputStream)
                val pdfDoc = PdfDocument(pdfReader)
                val numberOfPages = pdfDoc.numberOfPages
                
                val pagesToProcess = if (numberOfPages > 5) 5 else numberOfPages
                var extractedText = ""

                for (i in 1..pagesToProcess) {
                    extractedText += PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i))
                }

                pdfDoc.close()
                
                if (extractedText.isNotEmpty()) {
                    Toast.makeText(this, "تم استخراج النص بنجاح", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "المستند فارغ أو محمي", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            // لاحظ استخدام الباك سلاش هنا للهروب من فهم التيرمينال
            Toast.makeText(this, "خطأ في المعالجة: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode: Int, resultCode: Int, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            selectedFileUri = data?.data
            txtFileName.text = "تم التقاط الملف بنجاح"
            btnTranslate.visibility = View.VISIBLE
        }
    }
}
