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
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(Intent.createChooser(intent, "اختر ملف PDF"), 1001)
        }

        btnTranslate.setOnClickListener {
            if (selectedFileUri != null) {
                processPdf(selectedFileUri!!)
            }
        }
    }

    private fun processPdf(uri: Uri) {
        try {
            val iStream: InputStream? = contentResolver.openInputStream(uri)
            if (iStream != null) {
                val reader = PdfReader(iStream)
                val pdf = PdfDocument(reader)
                val pages = if (pdf.numberOfPages > 5) 5 else pdf.numberOfPages
                var resultText = ""
                for (i in 1..pages) {
                    resultText += PdfTextExtractor.getTextFromPage(pdf.getPage(i))
                }
                pdf.close()
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            selectedFileUri = data?.data
            txtFileName.text = "Selected"
            btnTranslate.visibility = View.VISIBLE
        }
    }
}
